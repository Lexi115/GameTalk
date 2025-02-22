package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.cookies.CookieHelper;
import it.unisa.studenti.nc8.gametalk.presentation.utils.cookies.CookieHelperImpl;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet che si occupa dell'autenticazione di un utente.
 */
public abstract class AuthenticationServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER =
            LoggerFactory.getLogger(AuthenticationServlet.class);

    /** La classe di servizio per gestire l'autenticazione. */
    private AuthenticationService authenticationService;

    /** Error handler. */
    private ErrorHandler errorHandler;

    /** La classe di servizio per recuperare gli utenti. */
    private UserService userService;

    /** Un helper per gestire i cookies. */
    private CookieHelper cookieHelper;

    /**
     * Init.
     */
    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");
        errorHandler = (ErrorHandler) ctx.getAttribute("errorHandler");

        this.authenticationService =
                serviceFactory.createAuthenticationService();
        this.userService = serviceFactory.createUserService();

        this.cookieHelper = new CookieHelperImpl();
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

    /**
     * Restituisce la classe cookie helper.
     *
     * @return La classe cookie helper.
     */
    protected CookieHelper getCookieHelper() {
        return cookieHelper;
    }

    /**
     * Restituisce l'handler di errori.
     *
     * @return L'handler di errori.
     */
    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }
}
