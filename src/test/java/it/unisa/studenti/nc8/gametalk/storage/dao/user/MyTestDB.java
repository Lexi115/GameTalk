package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyTestDB {

    private Database db;
    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws ConnectException {
        db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db", "mysql");

        DAOFactory daoFactory = new DAOFactoryImpl(db);
        userDAO = daoFactory.createUserDAO();
        ServiceFactory serviceFactory = new ServiceFactoryImpl(db, daoFactory);
        userService = serviceFactory.createUserService();
    }

    @Test
    void testDb() throws SQLException {
        try (Connection conn = db.connect()) {
            try (QueryResult qr = db.executeQuery(conn, "select * from users")) {
                ResultSet rs = qr.getResultSet();
                while (rs.next()) {
                    System.out.println(rs.getString("username"));
                }
            }
        }
    }

    @Test
    void testDao() throws DAOException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);
            User user = userDAO.get("ShadowPlays");
            if (user == null) return;

            user.setBanned(!user.isBanned());
            userDAO.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testService() throws ServiceException {
        try {
            userService.createUser("Pippo1001", "MyPassword10");
        } catch (IllegalArgumentException e) {
            User user = userService.findUserByUsername("Pippo1001");
            System.out.println(user);
        }
    }
}
