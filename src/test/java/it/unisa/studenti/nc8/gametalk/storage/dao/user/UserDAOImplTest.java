package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.business.models.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private UserDAOImpl userDAO;
    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db");
        userDAO = new UserDAOImpl(db, new UserMapper());

        db.connect();
        db.executeUpdate("DELETE FROM users");
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
        db.beginTransaction();
        User user = new User();
        user.setUsername("pasquale");
        user.setPassword("password_hashed");
        user.setCreationDate(LocalDate.now());
        user.setBanned(false);
        user.setRole(Role.Moderator);

        userDAO.save(user);
        ResultSet rs = db.executeQuery("SELECT * FROM users WHERE id = 1");
        db.commit();
        assertTrue(rs.next());
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