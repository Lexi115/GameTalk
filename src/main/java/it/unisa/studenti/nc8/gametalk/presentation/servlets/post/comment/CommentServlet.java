package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.comment;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet che si occupa della gestione dei commenti dei threads.
 */
public abstract class CommentServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER =
            LoggerFactory.getLogger(CommentServlet.class);

    /** Error handler. */
    private ErrorHandler errorHandler;

    /** La classe di servizio per recuperare i commenti. */
    private CommentService commentService;

    /**
     * Init.
     */
    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");
        errorHandler = (ErrorHandler) ctx.getAttribute("errorHandler");

        this.commentService = serviceFactory.createCommentService();
    }

    /**
     * Restituisce la classe di servizio commento.
     *
     * @return La classe di servizio commento.
     */
    protected CommentService getCommentService() {
        return commentService;
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
