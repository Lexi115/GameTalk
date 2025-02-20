package it.unisa.studenti.nc8.gametalk.storage.factories;

import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;

public interface DAOFactory {
    UserDAO createUserDAO();
    ThreadDAO createThreadDAO();
    CommentDAO createCommentDAO();
}
