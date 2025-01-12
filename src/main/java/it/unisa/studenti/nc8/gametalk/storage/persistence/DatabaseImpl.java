package it.unisa.studenti.nc8.gametalk.storage.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.*;
import java.util.TimeZone;


/**
 * Classe per la gestione della connessione e delle operazioni con un database SQL.
 * Utilizza un pool di connessioni per migliorare le prestazioni.
 *
 * @version 1.0
 */
public class DatabaseImpl implements Database {
    private static final Logger LOGGER = LogManager.getLogger();

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;

    private static DataSource dataSource;
    private Connection connection;

    /**
     * Costruttore con parametri di default per host (localhost) e porta (3306).
     *
     * @param username      Nome utente per accedere al database.
     * @param password      Password per accedere al database.
     * @param databaseName  Nome del database da utilizzare.
     */
    public DatabaseImpl(String username, String password, String databaseName) {
        this("localhost", 3306, username, password, databaseName);
    }

    /**
     * Costruttore con parametri personalizzati per host e porta.
     *
     * @param host          Host del database (es. localhost).
     * @param port          Porta del database (es. 3306).
     * @param username      Nome utente per accedere al database.
     * @param password      Password per accedere al database.
     * @param databaseName  Nome del database da utilizzare.
     */
    public DatabaseImpl(String host, int port, String username, String password, String databaseName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    /**
     * Crea una connessione al database.
     *
     * @throws SQLException Se la connessione fallisce.
     */
    @Override
    public void connect() throws SQLException {
        try {
            this.connection = this.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Connessione al database fallita!", e);
            throw new SQLException("Impossibile connettersi al database.", e);
        }
    }

    /**
     * Controlla se la connessione al database è attiva.
     *
     * @return true se la connessione è attiva, false altrimenti.
     */
    @Override
    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Chiude la connessione al database.
     *
     * @throws SQLException Se si verifica un errore durante la chiusura.
     */
    @Override
    public void close() throws SQLException {
        if (this.isConnected()) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOGGER.error("Errore durante la chiusura della connessione al database.", e);
                throw new SQLException("Errore durante la chiusura del database.", e);
            }
        }
    }

    /**
     * Esegue una query SELECT sul database.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il risultato della query come ResultSet.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(query)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Errore durante l'esecuzione della query: {}", query, e);
            throw new SQLException("Errore durante l'esecuzione della query.", e);
        }
    }

    /**
     * Esegue una query di tipo INSERT, UPDATE o DELETE sul database.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il numero di righe modificate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public int executeUpdate(String query, Object... parameters) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(query)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Errore durante l'esecuzione dell'update: {}", query, e);
            throw new SQLException("Errore durante l'esecuzione dell'update.", e);
        }
    }


    /**
     * Esegue una query di tipo INSERT e restituisce la chiave generata.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return La chiave primaria generata.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public int executeUpdateReturnKeys(String query, Object... parameters) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(query, true)) {
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Nessuna chiave generata trovata.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Errore durante l'esecuzione dell'update: {}", query, e);
            throw new SQLException("Errore durante l'esecuzione dell'update.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        if (dataSource == null) {
            PoolProperties p = new PoolProperties();
            p.setUrl(this.getUrl());
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(username);
            p.setPassword(password);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            dataSource = new DataSource();
            dataSource.setPoolProperties(p);
        }

        return dataSource.getConnection();
    }

    /**
     * Riempie i parametri in una query SQL preparata.
     *
     * @param statement  L'oggetto PreparedStatement da completare.
     * @param parameters I parametri da inserire.
     * @return Il PreparedStatement con i parametri settati.
     * @throws SQLException Se si verifica un errore nell'impostazione dei parametri.
     */
    private PreparedStatement fillParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

        return statement;
    }

    /**
     * Prepara una query SQL con i parametri forniti.
     *
     * @param query      La query SQL da preparare.
     * @param parameters I parametri da inserire.
     * @return L'oggetto PreparedStatement pronto per l'esecuzione.
     * @throws SQLException Se si verifica un errore durante la preparazione.
     */
    private PreparedStatement prepareStatement(String query, Object... parameters) throws SQLException {
        return this.prepareStatement(query, false, parameters);
    }

    /**
     * Prepara una query SQL con opzione per ottenere chiavi generate.
     *
     * @param query               La query SQL da preparare.
     * @param returnGeneratedKeys true se si vogliono restituire chiavi generate.
     * @param parameters          I parametri da inserire.
     * @return L'oggetto PreparedStatement pronto per l'esecuzione.
     * @throws SQLException Se si verifica un errore durante la preparazione.
     */
    private PreparedStatement prepareStatement(String query, boolean returnGeneratedKeys, Object... parameters)
            throws SQLException {
        if (!this.isConnected()) {
            throw new SQLException("Connessione al database non attiva.");
        }

        PreparedStatement statement = connection.prepareStatement(query, returnGeneratedKeys ?
                Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        return this.fillParameters(statement, parameters);
    }

    /**
     * Restituisce l'URL di connessione JDBC per il database.
     *
     * @return L'URL JDBC per la connessione.
     */
    private String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?serverTimezone="
                + TimeZone.getDefault().getID();
    }

    /**
     * Restituisce l'hostname di connessione JDBC per il database.
     * @return L'hostname per la connessione.
     */
    public String getHost() {
        return host;
    }

    /**
     * Restituisce la porta di connessione JDBC per il database.
     * @return La porta per la connessione.
     */
    public int getPort() {
        return port;
    }

    /**
     * Restituisce l'username di connessione JDBC per il database.
     * @return L'username per la connessione.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Restituisce la password di connessione JDBC per il database.
     * @return La porta per la connessione.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Restituisce il nome del database con il quale interagire.
     * @return Il nome del database.
     */
    public String getDatabaseName() {
        return databaseName;
    }
}
