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

public class DAOFactoryImpl implements DAOFactory {

    private Database database;

    public DAOFactoryImpl(final Database database) {
        this.database = database;
    }

    @Override
    public UserDAO createUserDAO() {
        return new UserDAOImpl(database, null, new UserMapper());
    }

    @Override
    public ThreadDAO createThreadDAO() {
        return new ThreadDAOImpl(database, null, new ThreadMapper());
    }

    @Override
    public CommentDAO createCommentDAO() {
        return new CommentDAOImpl(database, null, new CommentMapper());
    }
}
