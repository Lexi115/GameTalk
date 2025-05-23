package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet che si occupa della gestione degli utenti.
 */
public abstract class UserServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER =
            LoggerFactory.getLogger(UserServlet.class);

    /** Error handler. */
    private ErrorHandler errorHandler;

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
        errorHandler = (ErrorHandler) ctx.getAttribute("errorHandler");

        this.userService = serviceFactory.createUserService();
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
     * Restituisce l'handler di errori.
     *
     * @return L'handler di errori.
     */
    protected ErrorHandler getErrorHandler() {
        return errorHandler;
    }
}
