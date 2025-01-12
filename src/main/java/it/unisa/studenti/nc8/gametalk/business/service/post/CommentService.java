package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

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

    //TODO
    public List<Comment> getCommentsByThreadId(long threadId){
        if(threadId <= 0){
            throw new IllegalArgumentException("Id thread non valido");
        }
        return List.of();
    }

    public void updateComment(Comment comment) {
        try {
            commentDAO.update(comment);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
