package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.comment;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet che si occupa della gestione dei commenti dei threads.
 */
public abstract class CommentServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

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
}
