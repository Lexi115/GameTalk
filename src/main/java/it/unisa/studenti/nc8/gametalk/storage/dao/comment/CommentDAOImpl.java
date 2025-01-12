package it.unisa.studenti.nc8.gametalk.storage.dao.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDAOImpl extends DatabaseDAO<Comment> implements CommentDAO{

    public CommentDAOImpl(Database db, ResultSetMapper<Comment> mapper) {
        super(db, mapper);
    }

    @Override
    public Comment get(long id) throws DAOException {
        try {
            db.connect();
            Comment comment = null;

            String query = "SELECT * FROM comments WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);

            List<Comment> comments = mapper.map(rs);

            if(!comments.isEmpty()){
                comment = comments.getFirst();
            }

            db.close();
            return comment;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getAll() throws DAOException {
        try {
            db.connect();

            String query = "SELECT * FROM comments";

            ResultSet rs = db.executeQuery(query);

            List<Comment> comments = mapper.map(rs);

            db.close();
            return comments;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int save(Comment entity) throws DAOException {
        int rowsAffected;
        try {
            db.connect();
            String query = "INSERT INTO comments (thread_id, user_id, body, votes, creation_date) VALUES (?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getThreadId(),
                    entity.getUserId(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.getCreationDate()
            };

            rowsAffected = db.executeUpdate(query, params);
            db.close();

            return rowsAffected;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int update(Comment entity) throws DAOException {
        int rowsAffected;
        try {
            db.connect();
            String query = "UPDATE comments SET thread_id = ?, user_id = ?, body = ?, votes = ?, creation_date = ? WHERE id = ?";

            Object[] params = {
                    entity.getThreadId(),
                    entity.getUserId(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.getCreationDate(),
                    entity.getId()
            };

            rowsAffected = db.executeUpdate(query, params);
            db.close();
            return rowsAffected;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int delete(long id) throws DAOException {
        int rowsAffected;
        try {
            db.connect();
            String query = "DELETE FROM comments WHERE id = ?";

            rowsAffected = db.executeUpdate(query);
            db.close();
            return rowsAffected;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Comment> getCommentsByThreadId(long threadId,int page) throws DAOException {
        int limit = 10;
        int offset = (page - 1) * limit;

        try {
            db.connect();

            String query = "SELECT * FROM comments WHERE thread_id = ? LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, threadId, limit, offset);

            List<Comment> commentsList = mapper.map(rs);

            db.close();
            return commentsList;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
