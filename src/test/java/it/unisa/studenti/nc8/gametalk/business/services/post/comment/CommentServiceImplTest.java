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
    private final Validator<Comment> commentValidator = new CommentValidator(new RegexMatcher());
    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = mock(Database.class);
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

    // TC_CS1 - Thread valido con utente loggato
    @Test
    void findComments_ValidThread_LoggedInUser() throws Exception {
        long threadId = 1L;
        String username = "user123";
        int page = 1;
        int pageSize = 10;

        when(commentDAO.getCommentsByThreadId(threadId, username, page, pageSize))
                .thenReturn(List.of(new Comment()));

        List<Comment> result = commentService.findCommentsByThreadId(threadId, username, page, pageSize);
        assertFalse(result.isEmpty());
    }

    // TC_CS2 - Thread valido con utente nullo
    @Test
    void findComments_ValidThread_NullUser() throws Exception {
        long threadId = 1L;
        int page = 1;
        int pageSize = 10;

        when(commentDAO.getCommentsByThreadId(threadId, "", page, pageSize))
                .thenReturn(List.of(new Comment()));

        List<Comment> result = commentService.findCommentsByThreadId(threadId, null, page, pageSize);
        assertFalse(result.isEmpty());
    }

    // TC_CS3 - Thread valido con username vuoto
    @Test
    void findComments_ValidThread_EmptyUsername() throws Exception {
        long threadId = 1L;
        int page = 1;
        int pageSize = 10;

        when(commentDAO.getCommentsByThreadId(threadId, "", page, pageSize))
                .thenReturn(List.of(new Comment()));

        List<Comment> result = commentService.findCommentsByThreadId(threadId, "", page, pageSize);
        assertFalse(result.isEmpty());
    }

    // TC_CS4 - Thread ID non valido
    @Test
    void findComments_InvalidThreadId() {
        long threadId = 0;
        assertThrows(IllegalArgumentException.class,
                () -> commentService.findCommentsByThreadId(threadId, "user123", 1, 10));
    }

    // TC_CS5 - Pagina non valida
    @Test
    void findComments_InvalidPageNumber() {
        long threadId = 1L;
        assertThrows(IllegalArgumentException.class,
                () -> commentService.findCommentsByThreadId(threadId, "user123", 0, 10));
    }

    // TC_CS6 - Dimensione pagina non valida
    @Test
    void findComments_InvalidPageSize() {
        long threadId = 1L;
        assertThrows(IllegalArgumentException.class,
                () -> commentService.findCommentsByThreadId(threadId, "user123", 1, 0));
    }

    // TC_CS7 - Errore connessione DB
    @Test
    void findComments_DatabaseConnectionError() throws Exception {
        long threadId = 1L;
        int page = 1;
        int pageSize = 10;

        when(db.connect()).thenThrow(new SQLException("Errore connessione"));
        assertThrows(ServiceException.class,
                () -> commentService.findCommentsByThreadId(threadId, "user123", page, pageSize));
    }

    // TC_CS8 - Errore DAO
    @Test
    void findComments_DAOError() throws Exception {
        long threadId = 1L;
        int page = 1;
        int pageSize = 10;

        when(commentDAO.getCommentsByThreadId(anyLong(), anyString(), anyInt(), anyInt()))
                .thenThrow(new DAOException("Errore DAO"));
        assertThrows(ServiceException.class,
                () -> commentService.findCommentsByThreadId(threadId, "user123", page, pageSize));
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

    // TC_CS1 - commentId null
    @Test
    void rateComment_CommentIdNull() {
        long commentId = 0L; // commentId non valido
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS2 - commentId <= 0
    @Test
    void rateComment_CommentIdLessThanZero() {
        long commentId = -1L; // commentId non valido
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS3 - username null
    @Test
    void rateComment_UsernameNull() {
        long commentId = 1L;
        String username = null; // username non valido
        long threadId = 1L;
        int vote = 1;

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS4 - username vuoto
    @Test
    void rateComment_UsernameEmpty() {
        long commentId = 1L;
        String username = ""; // username non valido
        long threadId = 1L;
        int vote = 1;

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS5 - threadId <= 0
    @Test
    void rateComment_ThreadIdLessThanZero() {
        long commentId = 1L;
        String username = "user1";
        long threadId = -1L; // threadId non valido
        int vote = 1;

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS6 - voto non valido (>=2 o < -1)
    @Test
    void rateComment_InvalidVoteGreaterThanOne() {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 2; // voto non valido

        assertThrows(IllegalArgumentException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS7 - voto non valido (< -1)
    @Test
    void rateComment_InvalidVoteLessThanMinusOne() {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = -2; // voto non valido

        assertThrows(IllegalArgumentException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS8 - voto 0 (rimozione del voto)
    @Test
    void rateComment_VoteZero() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 0; // rimozione del voto
        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(userDAO.get(anyString())).thenReturn(new User());
        when(threadDAO.get(anyLong())).thenReturn(new Thread());

        assertDoesNotThrow(() -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS9 - thread archiviato
    @Test
    void rateComment_ArchivedThread() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        Thread thread = mock(Thread.class);
        when(threadDAO.get(anyLong())).thenReturn(thread);
        when(thread.isArchived()).thenReturn(true);

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS10 - errore commentDAO
    @Test
    void rateComment_CommentDAOError() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        when(commentDAO.get(anyLong())).thenThrow(new DAOException("Errore nel DAO"));

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS11 - errore userDAO
    @Test
    void rateComment_UserDAOError() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(userDAO.get(anyString())).thenThrow(new DAOException("Errore nel DAO"));

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS12 - errore threadDAO
    @Test
    void rateComment_ThreadDAOError() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        when(commentDAO.get(anyLong())).thenReturn(new Comment());
        when(userDAO.get(anyString())).thenReturn(new User());
        when(threadDAO.get(anyLong())).thenThrow(new DAOException("Errore nel DAO"));

        assertThrows(ServiceException.class, () -> commentService.rateComment(commentId, username, threadId, vote));
    }

    // TC_CS13 - voto positivo registrato
    @Test
    void rateComment_PositiveVote() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = 1;

        Comment comment = new Comment();
        when(commentDAO.get(anyLong())).thenReturn(comment);
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertDoesNotThrow(() -> commentService.rateComment(commentId, username, threadId, vote));
        verify(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());
    }

    // TC_CS14 - voto negativo registrato
    @Test
    void rateComment_NegativeVote() throws DAOException {
        long commentId = 1L;
        String username = "user1";
        long threadId = 1L;
        int vote = -1;

        Comment comment = new Comment();
        when(commentDAO.get(anyLong())).thenReturn(comment);
        when(threadDAO.get(anyLong())).thenReturn(new Thread());
        when(userDAO.get(anyString())).thenReturn(new User());
        doNothing().when(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());

        assertDoesNotThrow(() -> commentService.rateComment(commentId, username, threadId, vote));
        verify(commentDAO).voteComment(anyLong(), anyString(), anyLong(), anyInt());
    }

}