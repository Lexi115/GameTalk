package it.unisa.studenti.nc8.gametalk.dao.comment;

import it.unisa.studenti.nc8.gametalk.dao.DAO;
import it.unisa.studenti.nc8.gametalk.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.dao.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.post.comment.Comment;

import java.util.List;

public class CommentDAO extends DatabaseDAO<Comment> implements DAO<Comment> {

    public CommentDAO(Database db, ResultSetMapper<Comment> mapper) {
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
}
