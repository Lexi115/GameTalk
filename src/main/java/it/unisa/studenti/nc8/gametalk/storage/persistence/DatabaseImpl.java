package it.unisa.studenti.nc8.gametalk.storage.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Classe per la gestione della connessione e delle operazioni
 * con un database SQL. Utilizza un pool di connessioni per
 * migliorare le prestazioni.
 *
 * @version 1.0
 */
public class DatabaseImpl implements Database {
    /** Logger per registrare eventi e errori. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Host del database. */
    private final String host;

    /** Porta su cui è in ascolto il database. */
    private final int port;

    /** Nome utente per autenticarsi al database. */
    private final String username;

    /** Password per autenticarsi al database. */
    private final String password;

    /** Nome del database a cui connettersi. */
    private final String databaseName;

    /** Pool di connessioni per gestire le connessioni al database. */
    private static DataSource dataSource;

    /** Connessione attuale al database. */
    private Connection connection;

    /**
     * Costruttore.
     *
     * @param host         Host del database (es. localhost).
     * @param port          Porta del database (es. 3306).
     * @param username      Nome utente per accedere al database.
     * @param password      Password per accedere al database.
     * @param databaseName  Nome del database da utilizzare.
     */
    public DatabaseImpl(
            final String host,
            final int port,
            final String username,
            final String password,
            final String databaseName
    ) {
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
     * @return <code>true</code> se la connessione è attiva,
     * <code>false</code> altrimenti.
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
                LOGGER.info("Connessione al database chiusa.");
            } catch (SQLException e) {
                LOGGER.error("Errore durante la chiusura del database.", e);
                throw new SQLException(
                        "Errore durante la chiusura del database.", e);
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
    public ResultSet executeQuery(
            final String query,
            final Object... parameters
    ) throws SQLException {
        try {
            PreparedStatement statement = this.prepareStatement(
                    query, parameters);
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error(
                    "Errore durante l'esecuzione della query: {}", query, e);
            throw new SQLException(
                    "Errore durante l'esecuzione della query.", e);
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
    public int executeUpdate(
            final String query,
            final Object... parameters
    ) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(
                query, parameters)) {
            System.out.println(statement.toString());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(
                    "Errore durante l'esecuzione dell'update: {}", query, e);
            throw new SQLException(
                    "Errore durante l'esecuzione dell'update.", e);
        }
    }

    /**
     * Esegue una query di tipo INSERT e restituisce le chiavi generate.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Una lista di tutte le chiavi generate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public List<Long> executeInsert(
            final String query,
            final Object... parameters
    ) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(
                query, true, parameters)) {
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                List<Long> keys = new ArrayList<>();
                while (generatedKeys.next()) {
                    keys.add(generatedKeys.getLong(1));
                }
                return keys;
            }
        } catch (SQLException e) {
            LOGGER.error(
                    "Errore durante l'esecuzione dell'insert: {}", query, e);
            throw new SQLException(
                    "Errore durante l'esecuzione dell'insert.", e);
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
     * @throws SQLException Se si verifica un errore nell'impostazione
     * dei parametri.
     */
    private PreparedStatement fillParameters(
            final PreparedStatement statement,
            final Object... parameters
    ) throws SQLException {
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
    private PreparedStatement prepareStatement(
            final String query,
            final Object[] parameters
    ) throws SQLException {
        return this.prepareStatement(query, false, parameters);
    }

    /**
     * Prepara una query SQL con opzione per ottenere chiavi generate.
     *
     * @param query               La query SQL da preparare.
     * @param returnGeneratedKeys true se si vogliono restituire
     *                            chiavi generate.
     * @param parameters          I parametri da inserire.
     * @return L'oggetto PreparedStatement pronto per l'esecuzione.
     * @throws SQLException Se si verifica un errore durante la preparazione.
     */
    private PreparedStatement prepareStatement(
            final String query,
            final boolean returnGeneratedKeys,
            final Object[] parameters
    ) throws SQLException {
        if (!this.isConnected()) {
            throw new SQLException("Connessione al database non attiva.");
        }

        PreparedStatement statement = connection.prepareStatement(
                query, returnGeneratedKeys
                ? Statement.RETURN_GENERATED_KEYS
                        : Statement.NO_GENERATED_KEYS);
        return this.fillParameters(statement, parameters);
    }

    /**
     * Restituisce l'URL di connessione JDBC per il database.
     *
     * @return L'URL JDBC per la connessione.
     */
    private String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/"
                + databaseName + "?serverTimezone="
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

    /**
     * Avvia una nuova transazione.
     * <p>
     * Questo metodo prepara il contesto per eseguire una serie di operazioni
     * come un'unica unità di lavoro. Le modifiche non saranno persistenti
     * fino a quando non viene eseguito il commit.
     *
     * @throws SQLException Se si verifica un errore durante
     * l'avvio della transazione.
     */
    @Override
    public void beginTransaction() throws SQLException {
        if (connection.getAutoCommit()) {
            connection.setAutoCommit(false);
        }
    }

    /**
     * Conferma la transazione corrente.
     * <p>
     * Questo metodo applica in modo permanente tutte le operazioni eseguite
     * durante la transazione corrente. Dopo il commit, le modifiche non possono
     * essere annullate.
     *
     * @throws SQLException Se si verifica un errore durante
     * il commit della transazione.
     */
    @Override
    public void commit() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    /**
     * Annulla la transazione corrente.
     * <p>
     * Questo metodo annulla tutte le operazioni eseguite durante la transazione
     * corrente, ripristinando lo stato precedente.
     *
     * @throws SQLException Se si verifica un errore durante il rollback
     * della transazione.
     */
    @Override
    public void rollback() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}
