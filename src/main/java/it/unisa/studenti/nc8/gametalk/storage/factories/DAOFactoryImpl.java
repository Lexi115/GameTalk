package it.unisa.studenti.nc8.gametalk.storage.factories;

import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;

/**
 * Factory concreta per la creazione delle istanze DAO (Data Access Object).
 * Pensata per lavorare con un database.
 */
public class DAOFactoryImpl implements DAOFactory {

    /** Il database. */
    private final Database database;

    /**
     * Costruttore.
     *
     * @param database      Il database.
     */
    public DAOFactoryImpl(final Database database) {
        this.database = database;
    }

    /**
     * Crea e restituisce un'istanza di {@link UserDAO}.
     *
     * @return un'istanza di {@link UserDAO}
     */
    @Override
    public UserDAO createUserDAO() {
        return new UserDAOImpl(database, null, new UserMapper());
    }

    /**
     * Crea e restituisce un'istanza di {@link ThreadDAO}.
     *
     * @return un'istanza di {@link ThreadDAO}
     */
    @Override
    public ThreadDAO createThreadDAO() {
        return new ThreadDAOImpl(database, null, new ThreadMapper());
    }

    /**
     * Crea e restituisce un'istanza di {@link CommentDAO}.
     *
     * @return un'istanza di {@link CommentDAO}
     */
    @Override
    public CommentDAO createCommentDAO() {
        return new CommentDAOImpl(database, null, new CommentMapper());
    }
}
