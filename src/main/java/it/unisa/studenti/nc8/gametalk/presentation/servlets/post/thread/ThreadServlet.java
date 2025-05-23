package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet che si occupa della gestione dei threads.
 */
public abstract class ThreadServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER =
            LoggerFactory.getLogger(ThreadServlet.class);

    /** Error handler. */
    private ErrorHandler errorHandler;

    /** La classe di servizio per recuperare i thread. */
    private ThreadService threadService;

    /**
     * Init.
     */
    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");
        errorHandler = (ErrorHandler) ctx.getAttribute("errorHandler");

        this.threadService = serviceFactory.createThreadService();
    }

    /**
     * Restituisce la classe di servizio thread.
     *
     * @return La classe di servizio thread.
     */
    protected ThreadService getThreadService() {
        return threadService;
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
