package it.unisa.studenti.nc8.gametalk.storage.dao.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

public class CommentDAOImpl extends DatabaseDAO<Comment> implements CommentDAO{

    public CommentDAOImpl(Database db, ResultSetMapper<Comment> mapper) {
        super(db, mapper);
    }

    @Override
    public Comment get(long id) throws DAOException {
        return null;
    }

    @Override
    public List<Comment> getAll() throws DAOException {
        return List.of();
    }

    @Override
    public boolean save(Comment entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Comment entity) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        return false;
    }

    @Override
    public List<Comment> getCommentsByThreadId(long threadId) {
        return List.of();
    }
}
