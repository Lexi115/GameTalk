package it.unisa.studenti.nc8.gametalk.business.services.post.comment;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.comment.CommentValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
public class CommentServiceImplTest {
    private CommentServiceImpl commentService;
    private CommentDAO commentDAO;
    private ThreadDAO threadDAO;
    private UserDAO userDAO;
    private Validator<Comment> commentValidator = new CommentValidator(new RegexMatcher());

    @BeforeEach
    void setUp() throws SQLException {
        Database db = mock(Database.class);
        Connection connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(db.connect()).thenReturn(connection);
        when(db.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);
        commentDAO = mock(CommentDAO.class);
        threadDAO = mock(ThreadDAO.class);
        userDAO = mock(UserDAO.class);

        commentService = new CommentServiceImpl(db,commentDAO,threadDAO,userDAO, commentValidator);
    }


    @Test
    void addValidComment() throws DAOException, ServiceException {
        long threadId = 1L;
        String username = "Marco";
        String body = "Lorem ipsum dolor sit amet";

        Comment comment = new Comment();
        Thread thread = new Thread();
        when(threadDAO.get(anyLong())).thenReturn(thread);

        assertDoesNotThrow(() -> commentService.addComment(threadId,username,body));

    }

    @Test
    void deleteComment() {
    }

    @Test
    void findCommentById() {
    }

    @Test
    void findCommentsByThreadId() {
    }

    @Test
    void countCommentsByThreadId() {
    }

    @Test
    void rateComment() {
    }

    @Test
    void getPersonalVotes() {
    }
}