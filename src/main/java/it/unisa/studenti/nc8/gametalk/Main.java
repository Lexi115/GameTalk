package it.unisa.studenti.nc8.gametalk;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.config.Config;
import it.unisa.studenti.nc8.gametalk.config.EnvConfig;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.factories.DatabaseFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DatabaseFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

/**
 * Classe principale dell'applicazione che si occupa di
 * inizializzare le risorse necessarie (es. Database).
 */
public class Main extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Logger. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Main.class);

    /** Servlet context. */
    private ServletContext context;

    /** File di configurazione. */
    private Config config;

    /**
     * Metodo di inizializzazione.
     */
    @Override
    public void init() {
        LOGGER.info("Init Main lanciato!");
        context = getServletContext();
        config = EnvConfig.getInstance();

        try {
            // Inizializza service e storage layers.
            initLayers();
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante l'inizializzazione dell'applicazione", e);
        }

        LOGGER.info("Init Main terminato!");
    }

    private void initLayers() {
        DatabaseFactory databaseFactory = getDatabaseFactory();
        Database database = databaseFactory.createDatabase();

        // DAO e Service Factories
        DAOFactory daoFactory = new DAOFactoryImpl(database);
        ServiceFactory serviceFactory =
                new ServiceFactoryImpl(database, daoFactory);
        context.setAttribute("serviceFactory", serviceFactory);
    }

    private DatabaseFactory getDatabaseFactory() {
        // Inizializzazione database. Prende le credenziali
        // di accesso dal file "web.xml".
        String dbHost = config.get("DB_HOST");
        int dbPort = Integer.parseInt(config.get("DB_PORT"));
        String dbName = config.get("DB_NAME");
        String dbUser = config.get("DB_USER");
        String dbPassword = config.get("DB_PASSWORD");
        String dbType = config.get("DB_TYPE");
        return new DatabaseFactoryImpl(
                dbHost, dbPort, dbUser, dbPassword, dbName, dbType);
    }

    /**
     * Metodo di distruzione.
     */
    @Override
    public void destroy() {
        try {
            context.removeAttribute("serviceFactory");
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante lo spegnimento dell'applicazione", e);
        }
    }
}
