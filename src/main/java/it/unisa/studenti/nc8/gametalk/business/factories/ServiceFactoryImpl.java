package it.unisa.studenti.nc8.gametalk.business.factories;

import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.comment.CommentValidator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.thread.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.business.validators.user.UserValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;

/**
 * Factory concreta per la creazione delle classi di servizio.
 * Pensata per lavorare con un database e i DAO.
 */
public class ServiceFactoryImpl implements ServiceFactory {

    /** Il database. */
    private final Database database;

    /** La DAO Factory. */
    private final DAOFactory daoFactory;

    /**
     * Costruttore.
     *
     * @param database      Il database.
     * @param daoFactory    La factory per creare DAO.
     */
    public ServiceFactoryImpl(
            final Database database,
            final DAOFactory daoFactory
    ) {
        this.database = database;
        this.daoFactory = daoFactory;
    }

    /**
     * Crea e restituisce un'istanza del servizio di autenticazione.
     *
     * @return un'istanza di {@link AuthenticationService}
     */
    @Override
    public AuthenticationService createAuthenticationService() {
        UserDAO userDAO = daoFactory.createUserDAO();
        return new AuthenticationServiceImpl(database, userDAO);
    }

    /**
     * Crea e restituisce un'istanza del servizio utente.
     *
     * @return un'istanza di {@link UserService}
     */
    @Override
    public UserService createUserService() {
        UserDAO userDAO = daoFactory.createUserDAO();
        Validator<User> userValidator = new UserValidator();
        return new UserServiceImpl(database, userDAO, userValidator);
    }

    /**
     * Crea e restituisce un'istanza del servizio per la gestione dei thread.
     *
     * @return un'istanza di {@link ThreadService}
     */
    @Override
    public ThreadService createThreadService() {
        ThreadDAO threadDAO = daoFactory.createThreadDAO();
        UserDAO userDAO = daoFactory.createUserDAO();
        Validator<Thread> threadValidator = new ThreadValidator();
        return new ThreadServiceImpl(
                database, threadDAO, userDAO, threadValidator);
    }

    /**
     * Crea e restituisce un'istanza del servizio per la gestione dei commenti.
     *
     * @return un'istanza di {@link CommentService}
     */
    @Override
    public CommentService createCommentService() {
        CommentDAO commentDAO = daoFactory.createCommentDAO();
        ThreadDAO threadDAO = daoFactory.createThreadDAO();
        UserDAO userDAO = daoFactory.createUserDAO();
        Validator<Comment> commentValidator = new CommentValidator();
        return new CommentServiceImpl(
                database, commentDAO, threadDAO, userDAO, commentValidator);
    }
}
