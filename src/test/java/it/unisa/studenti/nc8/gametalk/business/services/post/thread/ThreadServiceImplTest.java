package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.thread.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
public class ThreadServiceImplTest {
    private ThreadServiceImpl threadService;
    private ThreadDAO threadDAO;
    private UserDAO userDAO;
    private Validator<Thread> threadValidator = new ThreadValidator(new RegexMatcher());

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
        threadDAO = mock(ThreadDAO.class);
        userDAO = mock(UserDAO.class);

        threadService = new ThreadServiceImpl(db,threadDAO,userDAO,threadValidator);
    }


    @Test
    void createThread() {
    }

    @Test
    void removeThread() {
    }

    @Test
    void updateThread() {
    }

    @Test
    void findThreadById() {
    }

    @Test
    void findThreads() {
    }

    @Test
    void findThreadsByUsername() {
    }

    @Test
    void rateThread() {
    }

    @Test
    void updateThreadCategory() {
    }

    @Test
    void archiveThread() {
    }

    @Test
    void getThreadCount() {
    }

    @Test
    void getPersonalVote() {
    }
}
