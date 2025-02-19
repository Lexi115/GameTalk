package it.unisa.studenti.nc8.gametalk.storage.dao.post.thread;

import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        threadDAO = new ThreadDAOImpl(database, connection);
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
        long id = 0;

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

    // todo - fare gli altri test cases
}