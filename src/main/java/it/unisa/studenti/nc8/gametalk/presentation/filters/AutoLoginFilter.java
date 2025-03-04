package it.unisa.studenti.nc8.gametalk.presentation.filters;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.cookies.CookieHelper;
import it.unisa.studenti.nc8.gametalk.presentation.utils.cookies.CookieHelperImpl;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Filtro che intercetta le richieste HTTP per gestire il login automatico e
 * controlla gli accessi alle pagine riservate ai moderatori.
 */
@WebFilter(filterName = "AutoLoginFilter")
public class AutoLoginFilter implements Filter {

    /** Logger. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AutoLoginFilter.class);

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
        ServletContext ctx = request.getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");
        AuthenticationService authenticationService =
                serviceFactory.createAuthenticationService();
        CookieHelper cookieHelper = new CookieHelperImpl();
        ErrorHandler errorHandler = (ErrorHandler) ctx.getAttribute("errorHandler");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // La sessione non esiste?
        if (session == null) {
            // Crea nuova sessione
            session = req.getSession();
            session.setAttribute("isModerator", false);

            //Verifica presenza authToken nel cookie per autologin
            //(altrimenti è un guest e ha solo la sessione)
            Cookie authTokenCookie = cookieHelper.getCookie("auth_token", req);
            if (authTokenCookie != null) {
                try {
                    User loggedUser = authenticationService
                            .loginByToken(authTokenCookie.getValue());

                    //Controllo se il login è avvenuto
                    if (loggedUser == null) {
                        errorHandler.handleError(req, res, HttpServletResponse.SC_UNAUTHORIZED, "Autenticazione fallita");
                        return;
                    }

                    //Controllo se è bannato
                    if (loggedUser.isBanned()) {
                        errorHandler.handleError(req, res, HttpServletResponse.SC_FORBIDDEN, "Sei stato bandito dal forum.");
                        return;
                    }

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
