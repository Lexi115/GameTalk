package it.unisa.studenti.nc8.gametalk.business.core;

import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
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
    private ServletContext ctx;

    /**
     * Metodo di inizializzazione.
     */
    @Override
    public void init() {
        LOGGER.info("Init Main lanciato!");
        ctx = getServletContext();

        try {
            /*
                Inizializzazione connessione database.
                Prende le credenziali di accesso dal file "web.xml".
             */
            String dbHost = ctx.getInitParameter("dbHost");
            int dbPort = Integer.parseInt(ctx.getInitParameter("dbPort"));
            String dbName = ctx.getInitParameter("dbName");
            String dbUser = ctx.getInitParameter("dbUser");
            String dbPassword = ctx.getInitParameter("dbPass");
            String dbType = ctx.getInitParameter("dbType");
            Database database = new DatabaseImpl(
                    dbHost, dbPort, dbUser, dbPassword, dbName, dbType);
            ctx.setAttribute("db", database);

            // DAO e Service Factories
            DAOFactory daoFactory = new DAOFactoryImpl(database);
            ServiceFactory serviceFactory =
                    new ServiceFactoryImpl(database, daoFactory);
            ctx.setAttribute("daoFactory", daoFactory);
            ctx.setAttribute("serviceFactory", serviceFactory);
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante l'inizializzazione dell'applicazione", e);
        }
    }

    /**
     * Metodo di distruzione.
     */
    @Override
    public void destroy() {
        try {
            ctx.removeAttribute("db");
            ctx.removeAttribute("daoFactory");
            ctx.removeAttribute("serviceFactory");
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante lo spegnimento dell'applicazione", e);
        }
    }
}
