package it.unisa.studenti.nc8.gametalk.business.core;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serial;

/**
 * Classe principale dell'applicazione che si occupa di
 * inizializzare le risorse necessarie (es. Database).
 */
public class Main extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /** Logger. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Servlet context. */
    private ServletContext ctx;

    /** Il database a cui collegarsi. */
    private Database database;

    /**
     * Metodo di inizializzazione.
     */
    @Override
    public void init() {
        LOGGER.info("Init lanciato!");
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
            database = new DatabaseImpl(
                    dbHost, dbPort, dbUser, dbPassword, dbName, dbType);
            ctx.setAttribute("db", database);
        } catch (Exception e) {
            LOGGER.error("Errore durante l'inizializzazione del database", e);
        }
    }

    /**
     * Metodo di distruzione.
     */
    @Override
    public void destroy() {
        try {
            if (database != null) {
                ctx.removeAttribute("db");
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante la chiusura del database", e);
        }
    }
}
