package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.comment.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentDAOImplTest {

    private Database database;
    private Connection connection;
    private ResultSet resultSet;

    private CommentDAO commentDAO;

    @BeforeEach
    void setUp() throws SQLException {
        database = mock(Database.class);
        connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        resultSet = mock(ResultSet.class);

        when(database.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);

        commentDAO = new CommentDAOImpl(database, connection, new CommentMapper());
    }

    @Test
    void testGetValidComment() throws DAOException, SQLException {
        long id = 1;

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        Comment comment = commentDAO.get(id);
        assertNotNull(comment);
        assertEquals(id, comment.getId());
    }

    @Test
    void testGetInvalidComment() throws DAOException, SQLException {
        long id = -1;

        when(resultSet.next()).thenReturn(false);

        Comment comment = commentDAO.get(id);
        assertNull(comment);
    }

    @Test
    void testGetAllComments() throws DAOException, SQLException {
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        List<Comment> comments = commentDAO.getAll();
        assertEquals(2, comments.size());
    }

    @Test
    void testSaveValidComment() throws DAOException, SQLException {
        Comment comment = new Comment();
        comment.setId(5L);
        comment.setUsername("op_user");
        comment.setBody("Corpo commento");

        commentDAO.save(comment);
        verify(database).executeInsert(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testSaveInvalidComment() throws SQLException {
        when(database.executeInsert(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        Comment comment = new Comment();
        comment.setId(-1L); // id non valido
        comment.setUsername("op_user");
        comment.setBody("Corpo commento");

        assertThrows(DAOException.class, () -> commentDAO.save(comment));
    }

    @Test
    void testUpdateValidThread() throws SQLException, DAOException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        Comment comment = new Comment();
        comment.setId(5L);
        comment.setBody("Corpo commento modificato");

        assertTrue(commentDAO.update(comment));
    }

    @Test
    void testUpdateInvalidComment() throws SQLException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        Comment comment = new Comment();
        comment.setId(-1L);

        assertThrows(DAOException.class, () -> commentDAO.update(comment));
    }

    @Test
    void testDeleteValidComment() throws DAOException, SQLException {
        long id = 2;
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        assertTrue(commentDAO.delete(id));
    }

    @Test
    void testDeleteInvalidThread() throws DAOException, SQLException {
        long id = -1;
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(0);

        assertFalse(commentDAO.delete(id));
    }

    @Test
    void testGetCommentsByThreadId() throws DAOException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(15L);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        List<Comment> comments = commentDAO.getCommentsByThreadId(
                5, "pluto", 1, 10);
        assertEquals(1, comments.size());
        assertEquals(15L, comments.getFirst().getId());
    }

    @Test
    void testCountCommentsByThreadId() throws DAOException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(1)).thenReturn(10L);

        long count = commentDAO.countCommentsByThreadId(6);
        assertEquals(10, count);
    }

    @Test
    void testVoteValidComment() throws DAOException, SQLException {
        commentDAO.voteComment(1L, "lucia", 1);
        verify(database).executeInsert(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testVoteInvalidComment() throws SQLException {
        when(database.executeInsert(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        assertThrows(DAOException.class, () -> commentDAO.voteComment(
                -1L, "lucia", 1));
    }

    @Test
    void testRemoveVoteValidThread() throws SQLException, DAOException {
        commentDAO.removeVoteComment(7L, "user123");
        verify(database).executeUpdate(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testRemoveVoteInvalidThread() throws SQLException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        assertThrows(DAOException.class, () -> commentDAO.removeVoteComment(-1L, "user123"));
    }
}
