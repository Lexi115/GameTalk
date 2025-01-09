package it.unisa.studenti.nc8.gametalk.service.post;

import it.unisa.studenti.nc8.gametalk.dao.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.dao.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.post.comment.Comment;

import java.util.List;

public class CommentService {
    private final CommentDAO commentDAO;

    public CommentService(Database db) {
        this.commentDAO = new CommentDAOImpl(db,new CommentMapper());
    }

    public Comment getCommentById(long id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("Id commento non valido");
            }

            return commentDAO.get(id);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

        public List<Comment> getAllComments(){
            try {
                return commentDAO.getAll();
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }

    //TODO paginazione anche qui
    public List<Comment> getCommentsByThreadId(long threadId){
        try {
            if(threadId <= 0){
                throw new IllegalArgumentException("Id thread non valido");
            }
            return commentDAO.getCommentsByThreadId(threadId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateComment(Comment comment) {
        try {
            commentDAO.update(comment);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
