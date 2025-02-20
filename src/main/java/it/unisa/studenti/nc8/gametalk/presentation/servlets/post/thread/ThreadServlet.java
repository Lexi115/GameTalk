package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.thread.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per recuperare i thread. */
    private ThreadService threadService;

    /**
     * Init.
     */
    @Override
    public void init() {
        Database db = Functions.getContextDatabase(this.getServletContext());
        ThreadDAO threadDAO = new ThreadDAOImpl(db, null);
        UserDAO userDAO = new UserDAOImpl(db, null);
        Validator<Thread> threadValidator = new ThreadValidator();

        this.threadService = new ThreadServiceImpl(
                db, threadDAO, userDAO, threadValidator);
    }

    /**
     * Restituisce la classe di servizio thread.
     * @return La classe di servizio thread.
     */
    protected ThreadService getThreadService() {
        return threadService;
    }
}
