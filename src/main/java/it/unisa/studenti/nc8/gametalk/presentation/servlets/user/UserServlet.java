package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet che si occupa della gestione degli utenti.
 */
public abstract class UserServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

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
}
