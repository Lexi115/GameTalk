package it.unisa.studenti.nc8.gametalk.persistence;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.config.Config;
import it.unisa.studenti.nc8.gametalk.config.EnvConfig;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.factories.DatabaseFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DatabaseFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Disabled("Questa test suite è pensata per essere eseguita su un db reale in locale!")
public class StorageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageTest.class);

    private Database db;
    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        Config config = EnvConfig.getInstance();
        String dbHost = config.get("DB_HOST");
        int dbPort = Integer.parseInt(config.get("DB_PORT"));
        String dbName = config.get("DB_NAME");
        String dbUser = config.get("DB_USER");
        String dbPassword = config.get("DB_PASSWORD");
        String dbType = config.get("DB_TYPE");
        DatabaseFactory databaseFactory = new DatabaseFactoryImpl(
                dbHost, dbPort, dbUser, dbPassword, dbName, dbType);
        db = databaseFactory.createDatabase();

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
                    LOGGER.info(rs.getString("username"));
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

            LOGGER.info("Banned: {} -> {}", user.isBanned(), !user.isBanned());
            user.setBanned(!user.isBanned());
            userDAO.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testTransaction() throws SQLException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                User user1 = new User();
                user1.setUsername("utente1");
                user1.setPassword("Password1@");
                user1.setRole(Role.Member);
                user1.setCreationDate(LocalDate.now());

                User user2 = new User();
                user2.setUsername("utente2");
                user2.setPassword("Password2@");
                user2.setRole(Role.Member);
                user2.setCreationDate(LocalDate.now());

                userDAO.save(user1);
                userDAO.save(user2);
                tx.commit();
            }

            try (Transaction tx = new TransactionImpl(connection)) {
                if (userDAO.delete("utente1") && userDAO.delete("utente2")) {
                    LOGGER.info("Utenti entrambi eliminati");
                    tx.commit();
                } else {
                    LOGGER.warn("Utenti non eliminati");
                }
            }
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testService() throws ServiceException {
        try {
            userService.createUser("Pippo1001", "MyPassword@1001");
            LOGGER.info("Utente creato");
        } catch (IllegalArgumentException e) {
            User user = userService.findUserByUsername("Pippo1001");
            LOGGER.info("Utente già creato: {}", user);
        }
    }
}
