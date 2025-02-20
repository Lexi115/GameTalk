package it.unisa.studenti.nc8.gametalk.business.services.auth;

import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.utils.Functions;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private AuthenticationServiceImpl authenticationService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        Database db = mock(Database.class);
        Connection connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        ResultSet resultSet = mock(ResultSet.class);
        userDAO = mock(UserDAO.class);

        when(db.connect()).thenReturn(connection);
        when(db.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);

        authenticationService = new AuthenticationServiceImpl(db, userDAO);
    }

    @Test
    void testLoginValidCredentials() throws ServiceException, AuthenticationException, DAOException {
        String username = "marco";
        String password = "password_marco";

        User user = new User();
        user.setUsername(username);
        user.setPassword(Functions.hash(password));
        when(userDAO.get(anyString())).thenReturn(user);

        User loggedUser = authenticationService.login(username, password);
        assertNotNull(loggedUser);
        assertEquals(username, loggedUser.getUsername());
    }

    @Test
    void testLoginInvalidCredentials() throws DAOException {
        String username = "marco";
        String password = "password_sbagliata";

        when(userDAO.get(anyString())).thenReturn(null);
        assertThrows(AuthenticationException.class,
                () -> authenticationService.login(username, password));
    }

    @Test
    void testLoginByValidToken() throws DAOException, ServiceException, AuthenticationException {
        String username = "marco";
        String token = "marco_token";

        User user = new User();
        user.setUsername(username);
        user.setAuthToken(Functions.hash(token));
        when(userDAO.getUserByToken(anyString())).thenReturn(user);

        User loggedUser = authenticationService.loginByToken(token);
        assertNotNull(loggedUser);
        assertEquals(username, loggedUser.getUsername());
    }

    @Test
    void testLoginByInvalidToken() throws DAOException {
        String token = "token_sbagliato";

        when(userDAO.getUserByToken(anyString())).thenReturn(null);
        assertThrows(AuthenticationException.class,
                () -> authenticationService.loginByToken(token));
    }
}
