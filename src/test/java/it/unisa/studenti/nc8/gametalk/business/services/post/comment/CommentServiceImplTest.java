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
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        commentService = new CommentServiceImpl(db, commentDAO, threadDAO, userDAO, commentValidator);
    }


    @Test
    void addValidComment() throws DAOException {
        long threadId = 1L;
        String username = "Marco";
        String body = "Lorem ipsum dolor sit amet";

        Thread thread = new Thread();
        when(threadDAO.get(anyLong())).thenReturn(thread);

        assertDoesNotThrow(() -> commentService.addComment(threadId, username, body));

    }

    @Test
    void addInvalidComment() throws DAOException {
        long threadId = 1L;
        String username = "Marco";
        String body = "Commento sotto post inesistente";

        when(threadDAO.get(anyLong())).thenReturn(null);

        assertThrows(ServiceException.class, () -> commentService.addComment(threadId, username, body));
    }

    @Test
    void deleteValidComment() throws DAOException {
        when(commentDAO.delete(anyLong())).thenReturn(true);
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(threadDAO.get(anyLong())).thenReturn(new Thread());

        assertDoesNotThrow(() -> commentService.deleteComment(1L));
    }

    @Test
    void deleteArchivedComment() throws DAOException {
        Thread archivedThread = new Thread();
        archivedThread.setArchived(true);
        when(commentDAO.delete(anyLong())).thenReturn(true);
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(threadDAO.get(anyLong())).thenReturn(archivedThread);

        assertThrows(ServiceException.class, () -> commentService.deleteComment(1L));
    }

    @Test
    void deleteInvalidComment() throws DAOException {
        when(commentDAO.delete(anyLong())).thenReturn(true);
        when(commentDAO.get(anyLong())).thenReturn(null);
        when(threadDAO.get(anyLong())).thenReturn(new Thread());

        assertThrows(ServiceException.class, () -> commentService.deleteComment(1L));
    }


    @Test
    void findValidCommentById() throws ServiceException, DAOException {
        Comment comment = new Comment();
        comment.setThreadId(1L);
        comment.setUsername("Marco");
        comment.setBody("Commento recuperato");

        when(commentDAO.get(anyLong())).thenReturn(comment);

        Comment recoveredComment = commentService.findCommentById(1L);
        assertNotNull(recoveredComment);
        assertEquals("Commento recuperato", recoveredComment.getBody());
    }

    @Test
    void findInvalidCommentById() throws ServiceException, DAOException {
        when(commentDAO.get(anyLong())).thenReturn(null);

        Comment recoveredComment = commentService.findCommentById(1L);
        assertNull(recoveredComment);
    }

    @Test
    void findValidCommentsByThreadId() throws ServiceException, DAOException {
        Comment comment = new Comment();
        comment.setThreadId(1L);
        comment.setUsername("Marco");
        comment.setBody("Commento recuperato del richiedente");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        when(commentDAO.getCommentsByThreadId(anyLong(),anyString(),anyInt(),anyInt())).thenReturn(comments);
        List<Comment> recoveredComments = commentService.findCommentsByThreadId(1L,"Marco",1,18);

        assertNotNull(recoveredComments);
        assertEquals(comments, recoveredComments);
    }

    @Test
    void findInvalidCommentsByThreadId() throws ServiceException, DAOException {
        //Lista vuota, nessun commento trovato
        List<Comment> comments = new ArrayList<>();


        when(commentDAO.getCommentsByThreadId(anyLong(),anyString(),anyInt(),anyInt())).thenReturn(comments);
        List<Comment> recoveredComments = commentService.findCommentsByThreadId(1L,"Marco",1,18);

        assertNotNull(recoveredComments);
        assertEquals(comments, recoveredComments);
    }

    @Test
    void countCommentsByThreadId() throws DAOException {
        when(commentDAO.countCommentsByThreadId(anyLong())).thenReturn(20L);

        assertDoesNotThrow(() -> commentService.countCommentsByThreadId(1L));
    }

    @Test
    void rateValidComment() throws DAOException {
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertDoesNotThrow(() -> commentService.rateComment(1L,"Marco",1L,1));
        verify(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());
        verify(commentDAO, never()).removeVoteComment(anyLong(),anyString());
    }

    @Test
    void unrateValidComment() throws DAOException {
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertDoesNotThrow(() -> commentService.rateComment(1L,"Marco",1L,0));
        verify(commentDAO, never()).voteComment(anyLong(), anyString(), anyLong(), anyInt());
        verify(commentDAO).removeVoteComment(anyLong(),anyString());
    }

    @Test
    void rateInvalidComment() throws DAOException {
        when(commentDAO.get(anyLong())).thenReturn(null);
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertThrows(ServiceException.class, () -> commentService.rateComment(1L,"Marco",1L,0));
    }

    @Test
    void rateArchivedComment() throws DAOException {
        Thread archivedThread = new Thread();
        archivedThread.setArchived(true);
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(threadDAO.get(anyLong())).thenReturn(archivedThread);
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertThrows(ServiceException.class, () -> commentService.rateComment(1L,"Marco",1L,0));
    }



    @Test
    void getValidPersonalVotes() throws DAOException {
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(commentDAO.getPersonalVotes(anyLong(),anyString())).thenReturn(new HashMap<>());

        Map<Long, Integer> votes = assertDoesNotThrow(() -> commentService.getPersonalVotes(1L,"Marco"));
        assertNotNull(votes);
    }
}