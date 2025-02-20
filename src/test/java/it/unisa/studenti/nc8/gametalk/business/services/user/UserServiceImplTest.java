package it.unisa.studenti.nc8.gametalk.business.services.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.validators.user.UserValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        Database database = mock(Database.class);
        Connection connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(database.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);

        UserDAO userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(database, userDAO, new UserValidator());
    }

    @Test
    void createUser() throws ServiceException {
        userService.createUser("pippo", "password_pippo");
    }

    @Test
    void removeUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateToken() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findUsersByUsername() {
    }

    @Test
    void findBannedUsers() {
    }

    @Test
    void banUser() {
    }
}