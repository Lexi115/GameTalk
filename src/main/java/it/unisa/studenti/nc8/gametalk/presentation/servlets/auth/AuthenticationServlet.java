package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet che si occupa dell'autenticazione di un utente.
 */
public abstract class AuthenticationServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per gestire l'autenticazione. */
    private AuthenticationService authenticationService;

    /** La classe di servizio per recuperare gli utenti. */
    private UserService userService;

    /**
     * Init.
     */
    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");

        this.authenticationService =
                serviceFactory.createAuthenticationService();
        this.userService = serviceFactory.createUserService();
    }

    /**
     * Restituisce la classe di servizio per l'autenticazione.
     *
     * @return La classe di servizio per l'autenticazione.
     */
    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    /**
     * Restituisce la classe di servizio utente.
     *
     * @return La classe di servizio utente.
     */
    protected UserService getUserService() {
        return userService;
    }
}
