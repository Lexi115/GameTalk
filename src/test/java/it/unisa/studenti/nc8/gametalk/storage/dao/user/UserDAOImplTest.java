package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db", "mysql");
        try (Connection connection = db.connect()) {
            db.executeUpdate(connection, "DELETE FROM users");
        }
    }

    @Test
    void get() {
        String query = "SELECT * FROM users WHERE id = ?";
    }

    @Test
    void getAll() {
    }

    @Test
    void save() throws DAOException, SQLException {
        try (Connection connection = db.connect()) {
            UserDAO userDAO = new UserDAOImpl(db, connection);

            User user = new User();
            user.setUsername("pasquale");
            user.setPassword("password_hashed");
            user.setCreationDate(LocalDate.now());
            user.setBanned(false);
            user.setRole(Role.Moderator);

            userDAO.save(user);
            String query = "SELECT * FROM users WHERE id = 1";
            try (QueryResult qr = db.executeQuery(connection, query)) {
                assertTrue(qr.getResultSet().next());
            }
        }
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getUsersByUsername() {
    }

    @Test
    void getBannedUsers() {
    }
}