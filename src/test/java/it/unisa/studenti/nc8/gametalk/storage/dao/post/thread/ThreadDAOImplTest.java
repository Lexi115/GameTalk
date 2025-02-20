package it.unisa.studenti.nc8.gametalk.storage.dao.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.thread.ThreadMapper;
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

class ThreadDAOImplTest {

    private Database database;
    private Connection connection;
    private ResultSet resultSet;

    private ThreadDAOImpl threadDAO;

    @BeforeEach
    void setUp() throws SQLException {
        database = mock(Database.class);
        connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        resultSet = mock(ResultSet.class);

        when(database.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);

        threadDAO = new ThreadDAOImpl(database, connection, new ThreadMapper());
    }

    @Test
    void testGetValidThread() throws DAOException, SQLException {
        long id = 1;

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("category")).thenReturn("General");

        Thread thread = threadDAO.get(id);
        assertNotNull(thread);
        assertEquals(id, thread.getId());
    }

    @Test
    void testGetInvalidThread() throws DAOException, SQLException {
        long id = -1;

        when(resultSet.next()).thenReturn(false);

        Thread thread = threadDAO.get(id);
        assertNull(thread);
    }

    @Test
    void testGetAllThreads() throws DAOException, SQLException {
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("category")).thenReturn("General");

        List<Thread> threads = threadDAO.getAll();
        assertEquals(2, threads.size());
    }

    @Test
    void testSaveValidThread() throws DAOException, SQLException {
        Thread thread = new Thread();
        thread.setId(5L);
        thread.setUsername("op_user");
        thread.setTitle("Titolo thread");
        thread.setBody("Corpo thread");
        thread.setCategory(Category.Welcome);

        threadDAO.save(thread);
        verify(database).executeInsert(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testSaveInvalidThread() throws SQLException {
        when(database.executeInsert(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        Thread thread = new Thread();
        thread.setId(-1L);
        thread.setCategory(Category.Welcome);

        assertThrows(DAOException.class, () -> threadDAO.save(thread));
    }

    @Test
    void testUpdateValidThread() throws SQLException, DAOException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        Thread thread = new Thread();
        thread.setId(1L);
        thread.setCategory(Category.Announcements);

        assertTrue(threadDAO.update(thread));
    }

    @Test
    void testUpdateInvalidThread() throws SQLException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        Thread thread = new Thread();
        thread.setId(-1L); // id non valido
        thread.setCategory(Category.Memes);

        assertThrows(DAOException.class, () -> threadDAO.update(thread));
    }

    @Test
    void testDeleteValidThread() throws DAOException, SQLException {
        long id = 2;
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        assertTrue(threadDAO.delete(id));
    }

    @Test
    void testDeleteInvalidThread() throws DAOException, SQLException {
        long id = -1;
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(0);

        assertFalse(threadDAO.delete(id));
    }

    @Test
    void testGetThreadsByTitle() throws DAOException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("title")).thenReturn("Test Thread");
        when(resultSet.getString("category")).thenReturn("General");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        List<Thread> threads = threadDAO.getThreadsByTitle("Test", Category.General, 1, 10, Order.Best, null, null);
        assertEquals(1, threads.size());
        assertEquals("Test Thread", threads.getFirst().getTitle());
    }

    @Test
    void testGetThreadsByCategory() throws DAOException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(30L);
        when(resultSet.getString("category")).thenReturn("General");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        List<Thread> threads = threadDAO.getThreadsByCategory(Category.General, 1, 10, Order.Best, null, null);
        assertEquals(1, threads.size());
        assertEquals(Category.General, threads.getFirst().getCategory());
    }

    @Test
    void testGetThreadsByUsername() throws DAOException, SQLException {
        String username = "giovanni";
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(3L);
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getString("category")).thenReturn("Memes");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));

        List<Thread> threads = threadDAO.getThreadsByUsername(username, 1, 10, Order.Best);
        assertEquals(1, threads.size());
        assertEquals(username, threads.getFirst().getUsername());
    }

    @Test
    void testVoteValidThread() throws DAOException, SQLException {
        threadDAO.voteThread(1L, "nello", 1);
        verify(database).executeInsert(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testVoteInvalidThread() throws SQLException {
        when(database.executeInsert(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        assertThrows(DAOException.class, () -> threadDAO.voteThread(0L, "nello", 1));
    }

    @Test
    void testRemoveVoteValidThread() throws SQLException, DAOException {
        threadDAO.removeVoteThread(100L, "user123");
        verify(database).executeUpdate(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testRemoveVoteInvalidThread() throws SQLException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        assertThrows(DAOException.class, () -> threadDAO.removeVoteThread(-1L, "user123"));
    }
}