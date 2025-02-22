package it.unisa.studenti.nc8.gametalk.business.services.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.NotFoundException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.utils.hashing.Hasher;
import it.unisa.studenti.nc8.gametalk.business.validators.user.UserValidator;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        Database database = mock(Database.class);
        Connection connection = mock(Connection.class);
        QueryResult queryResult = mock(QueryResult.class);
        ResultSet resultSet = mock(ResultSet.class);
        Hasher passwordHasher = mock(Hasher.class);
        userDAO = mock(UserDAO.class);

        when(database.connect()).thenReturn(connection);
        when(database.executeQuery(eq(connection), anyString(), any(Object[].class)))
                .thenReturn(queryResult);
        when(queryResult.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(passwordHasher.hash(anyString())).thenReturn("hashed_password");

        userService = new UserServiceImpl(database, userDAO, new UserValidator(), passwordHasher);
    }

    @Test
    void testCreateValidUser() throws ServiceException, DAOException {
        userService.createUser("pippo", "PasswordPippo@1001");
        verify(userDAO).save(any(User.class));
    }

    @Test
    void testCreateInvalidUser() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser("", ""));
    }

    @Test
    void testRemoveValidUser() throws DAOException, ServiceException {
        when(userDAO.delete(anyString())).thenReturn(true);
        userService.removeUser("pippo");
        verify(userDAO).delete(anyString());
    }

    @Test
    void testRemoveInvalidUser() {
        assertThrows(NotFoundException.class,
                () -> userService.removeUser("utente_sconosciuto"));
    }

    @Test
    void testUpdateValidPassword() throws ServiceException, DAOException {
        User user = new User();
        user.setUsername("pippo");
        user.setPassword("PasswordPippo@1001");
        user.setCreationDate(LocalDate.now());

        when(userDAO.get(anyString())).thenReturn(user);
        userService.updatePassword("pippo", "NuovaPassword@1234");
        verify(userDAO).update(any(User.class));
    }

    @Test
    void testUpdateInvalidPassword() throws DAOException {
        User user = new User();
        user.setUsername("pippo");
        user.setPassword("PasswordPippo@1001");
        user.setCreationDate(LocalDate.now());

        when(userDAO.get(anyString())).thenReturn(user);
        assertThrows(IllegalArgumentException.class,
                () -> userService.updatePassword("pippo", ""));
    }

    @Test
    void testUpdateToken() throws DAOException, ServiceException {
        User user = new User();
        user.setUsername("pippo");

        when(userDAO.get(anyString())).thenReturn(user);
        userService.updateToken("pippo", "new_token");
        verify(userDAO).update(any(User.class));
    }

    @Test
    void testFindUserByUsername() throws DAOException, ServiceException {
        User user = new User();
        user.setUsername("pippo");

        when(userDAO.get(anyString())).thenReturn(user);
        User userFound = userService.findUserByUsername("pippo");
        assertEquals(user.getUsername(), userFound.getUsername());
    }

    @Test
    void testFindUsersByUsername() throws DAOException, ServiceException {
        String username = "%Gamer%";

        User user1 = new User();
        user1.setUsername("CiccioGamer");
        User user2 = new User();
        user2.setUsername("GamerAlpha");
        List<User> users = Arrays.asList(user1, user2);

        when(userDAO.getUsersByUsername(eq(username), anyInt(), anyInt())).thenReturn(users);
        List<User> usersFound = userService.findUsersByUsername(username, 1, 10);
        assertEquals(2, usersFound.size());
        usersFound.forEach(user -> assertTrue(user.getUsername().contains(
                username.replaceAll("%", ""))));
    }

    @Test
    void testFindBannedUsers() throws DAOException, ServiceException {
        User user = new User();
        user.setUsername("pippo");
        user.setBanned(true);
        List<User> users = List.of(user);

        when(userDAO.getBannedUsers(anyInt(), anyInt())).thenReturn(users);
        List<User> bannedUsers = userService.findBannedUsers(1, 10);
        assertEquals(1, bannedUsers.size());
        assertEquals(user.getUsername(), bannedUsers.getFirst().getUsername());
        assertTrue(bannedUsers.getFirst().isBanned());
    }

    @Test
    void testBanUser() throws DAOException, ServiceException {
        User user = new User();
        user.setUsername("pippo");
        user.setBanned(false);

        when(userDAO.get(user.getUsername())).thenReturn(user);
        userService.banUser(user.getUsername(), true);
        verify(userDAO).update(any(User.class));
    }
}
