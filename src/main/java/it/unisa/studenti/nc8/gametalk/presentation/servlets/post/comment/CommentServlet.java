package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.comment;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.comment.CommentValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        Database db = Functions.getContextDatabase(this.getServletContext());
        CommentDAO commentDAO = new CommentDAOImpl(db, null);
        ThreadDAO threadDAO = new ThreadDAOImpl(db, null);
        UserDAO userDAO = new UserDAOImpl(db, null);
        Validator<Comment> commentValidator = new CommentValidator();

        this.commentService = new CommentServiceImpl(
                db, commentDAO, threadDAO, userDAO, commentValidator);
    }

    /**
     * Restituisce la classe di servizio commento.
     * @return La classe di servizio commento.
     */
    protected CommentService getCommentService() {
        return commentService;
    }
}
