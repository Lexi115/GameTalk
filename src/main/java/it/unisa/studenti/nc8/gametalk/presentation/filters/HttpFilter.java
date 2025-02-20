package it.unisa.studenti.nc8.gametalk.presentation.filters;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.validators.user.UserValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationServiceImpl;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Filtro che intercetta le richieste HTTP per gestire il login automatico e
 * controlla gli accessi alle pagine riservate ai moderatori.
 */
@WebFilter(filterName = "HttpFilter")
public class HttpFilter implements Filter {

    /** Logger per la gestione dei log. */
    private static final Logger LOGGER = LogManager.getLogger();


    /**
     * Intercetta le richieste HTTP per gestire l'autenticazione tramite
     * token di autenticazione e controllo sugli accessi.
     *
     * @param request  la richiesta HTTP in entrata.
     * @param response la risposta HTTP in uscita.
     * @param chain    la catena di filtri a cui delegare la richiesta.
     * @throws ServletException se si verifica un errore generico.
     * @throws IOException se si verifica un errore I/O.
     */
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Database db = Functions.getContextDatabase(req.getServletContext());
        DAOFactory daoFactory = new DAOFactoryImpl(db);
        ServiceFactory serviceFactory = new ServiceFactoryImpl(db, daoFactory);
        AuthenticationService authenticationService =
                serviceFactory.createAuthenticationService();

        HttpSession session = req.getSession(false);

        // La sessione non esiste?
        if (session == null) {
            // Crea nuova sessione
            session = req.getSession();
            session.setAttribute("isModerator", false);

            //Verifica presenza authToken nel cookie per autologin
            //(altrimenti è un guest e ha solo la sessione)
            Cookie authTokenCookie = Functions.getCookie("auth_token", req);
            if (authTokenCookie != null) {
                try {
                    User loggedUser = authenticationService
                            .loginByToken(authTokenCookie.getValue());

                    //Controllo se è un admin
                    boolean isMod = loggedUser.getRole() == Role.Moderator;
                    session.setAttribute("user", loggedUser);
                    session.setAttribute("isModerator", isMod);

                } catch (AuthenticationException e) {
                    res.sendRedirect(req.getContextPath() + "/error/401");
                } catch (ServiceException e) {
                    LOGGER.error("Errore con servizio di autenticazione", e);
                    res.sendRedirect(req.getContextPath() + "/error/500");
                }

            }
        }

        chain.doFilter(req, res);
    }
}
