package it.unisa.studenti.nc8.gametalk.storage.dao.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

public interface CommentDAO extends DAO<Comment> {
    List<Comment> getCommentsByThreadId(long threadId, int page) throws DAOException;
}
