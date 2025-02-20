package it.unisa.studenti.nc8.gametalk.storage.factories;

import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;

/**
 * Factory astratta per la creazione delle istanze DAO (Data Access Object).
 */
public interface DAOFactory {

    /**
     * Crea e restituisce un'istanza di {@link UserDAO}.
     *
     * @return un'istanza di {@link UserDAO}
     */
    UserDAO createUserDAO();

    /**
     * Crea e restituisce un'istanza di {@link ThreadDAO}.
     *
     * @return un'istanza di {@link ThreadDAO}
     */
    ThreadDAO createThreadDAO();

    /**
     * Crea e restituisce un'istanza di {@link CommentDAO}.
     *
     * @return un'istanza di {@link CommentDAO}
     */
    CommentDAO createCommentDAO();
}
