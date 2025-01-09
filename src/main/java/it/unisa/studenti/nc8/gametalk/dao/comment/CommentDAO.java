package it.unisa.studenti.nc8.gametalk.dao.comment;

import it.unisa.studenti.nc8.gametalk.dao.DAO;
import it.unisa.studenti.nc8.gametalk.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.post.comment.Comment;

import java.util.List;

public interface CommentDAO extends DAO<Comment> {
    List<Comment> getCommentsByThreadId(long threadId) throws DAOException;
}
