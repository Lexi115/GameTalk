package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOImplTest {

    private Database database;
    private Connection connection;
    private ResultSet resultSet;

    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        database = mock(Database.class);
        connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        resultSet = mock(ResultSet.class);

        when(database.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);

        userDAO = new UserDAOImpl(database, connection);
    }

    @Test
    void testGetValidUser() throws SQLException, DAOException {
        String username = "test_user";

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("role")).thenReturn("Member");

        User user = userDAO.get(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    void testGetInvalidUser() throws SQLException, DAOException {
        String username = "test_invalid_user";
        when(resultSet.next()).thenReturn(false);

        User user = userDAO.get(username);
        assertNull(user);
    }

    @Test
    void testGetAllUsers() throws SQLException, DAOException {
        when(resultSet.next())
                .thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("username")).thenReturn("test_user");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("role")).thenReturn("Member");

        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void testSaveValidUser() throws DAOException, SQLException {
        User user = new User();
        user.setUsername("pasquale");
        user.setPassword("password_hashed");
        user.setCreationDate(LocalDate.now());
        user.setBanned(false);
        user.setRole(Role.Moderator);

        userDAO.save(user);
        verify(database).executeInsert(eq(connection), anyString(), any(Object[].class));
    }

    @Test
    void testSaveInvalidUser() throws SQLException {
        when(database.executeInsert(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("password_hashed");
        user.setCreationDate(LocalDate.now());
        user.setBanned(false);
        user.setRole(Role.Moderator);

        assertThrows(DAOException.class, () -> userDAO.save(user));
    }

    @Test
    void testUpdateValidUser() throws SQLException, DAOException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        User user = new User();
        user.setRole(Role.Member);
        user.setPassword("password_changed");

        assertTrue(userDAO.update(user));
    }

    @Test
    void testUpdateInvalidUser() throws SQLException {
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenThrow(SQLException.class);

        User user = new User();
        user.setRole(Role.Member);
        user.setUsername("username_already_in_use");

        assertThrows(DAOException.class, () -> userDAO.update(user));
    }

    @Test
    void testDeleteValidUser() throws DAOException, SQLException {
        String username = "test_user";
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(1);

        assertTrue(userDAO.delete(username));
    }

    @Test
    void testDeleteInvalidUser() throws DAOException, SQLException {
        String username = "test_invalid_user";
        when(database.executeUpdate(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(0);

        assertFalse(userDAO.delete(username));
    }

    @Test
    void testGetUsersByUsername() throws SQLException, DAOException {
        String username = "%Gamer%";

        User user1 = new User();
        user1.setUsername("CiccioGamer");
        User user2 = new User();
        user2.setUsername("GamerAlpha");

        when(resultSet.next()).thenReturn(true)
                .thenReturn(true).thenReturn(false);
        when(resultSet.getString("username"))
                .thenReturn(user1.getUsername()).thenReturn(user2.getUsername());
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("role")).thenReturn("Member");

        List<User> users = userDAO.getUsersByUsername(username, 1, 10);
        assertEquals(2, users.size());
        users.forEach(user -> assertTrue(user.getUsername().contains(
                username.replaceAll("%", ""))));
    }

    @Test
    void testGetBannedUsers() throws SQLException, DAOException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("username")).thenReturn("test_banned_user");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("role")).thenReturn("Member");
        when(resultSet.getBoolean("banned")).thenReturn(true);

        List<User> bannedUsers = userDAO.getBannedUsers(1, 10);
        assertEquals(1, bannedUsers.size());
        assertTrue(bannedUsers.getFirst().isBanned());
    }

    @Test
    void testGetValidUserByToken() throws SQLException, DAOException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("username")).thenReturn("test_token_user");
        when(resultSet.getDate("creation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("role")).thenReturn("Member");
        when(resultSet.getString("auth_token")).thenReturn("test_token");

        String token = "test_token";
        User user = userDAO.getUserByToken(token);
        assertNotNull(user);
        assertEquals(token, user.getAuthToken());
    }

    @Test
    void testGetInvalidUserByToken() throws SQLException, DAOException {
        when(resultSet.next()).thenReturn(false);

        String token = "test_invalid_token";
        User user = userDAO.getUserByToken(token);
        assertNull(user);
    }
}
