package it.unisa.studenti.nc8.gametalk.storage.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
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
 * @version 2.0
 */
public class DatabaseImpl implements Database {

    /** Logger. */
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

    /** Pool per gestire le connessioni al database. */
    private final DataSource dataSource;

    /**
     * Costruttore.
     *
     * @param host         Host del database (es. localhost).
     * @param port         Porta del database (es. 3306).
     * @param username     Nome utente per accedere al database.
     * @param password     Password per accedere al database.
     * @param databaseName Nome del database da utilizzare.
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
        this.dataSource = initDataSource();
    }

    @Override
    public Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Esegue una query SELECT sul database.
     *
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il risultato della query.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public QueryResult executeQuery(
            final Connection connection,
            final String query,
            final Object... parameters
    ) throws SQLException {
        try {
            PreparedStatement statement =
                    prepareStatement(connection, query, parameters);
            ResultSet resultSet = statement.executeQuery();
            return new QueryResultImpl(resultSet, statement);
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
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il numero di righe modificate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public int executeUpdate(
            final Connection connection,
            final String query,
            final Object... parameters
    ) throws SQLException {
        try (PreparedStatement statement =
                     prepareStatement(connection, query, parameters)) {
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
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Una lista di tutte le chiavi generate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    @Override
    public List<Object> executeInsert(
            final Connection connection,
            final String query,
            final Object... parameters
    ) throws SQLException {
        try (PreparedStatement statement =
                     prepareStatement(connection, query, true, parameters)) {
            statement.executeUpdate();
            return getGeneratedKeys(statement);
        } catch (SQLException e) {
            LOGGER.error(
                    "Errore durante l'esecuzione dell'insert: {}", query, e);
            throw new SQLException(
                    "Errore durante l'esecuzione dell'insert.", e);
        }
    }

    private DataSource initDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getUrl());
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(100);
        config.setMinimumIdle(10);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(2500);
        config.setLeakDetectionThreshold(5000);
        config.setConnectionTestQuery("SELECT 1");

        // Ottimizzazioni
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.setConnectionInitSql("SET autocommit=true");

        return new HikariDataSource(config);
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
     * @param connection La connessione al database.
     * @param query      La query SQL da preparare.
     * @param parameters I parametri da inserire.
     * @return L'oggetto PreparedStatement pronto per l'esecuzione.
     * @throws SQLException Se si verifica un errore durante la preparazione.
     */
    private PreparedStatement prepareStatement(
            final Connection connection,
            final String query,
            final Object[] parameters
    ) throws SQLException {
        return this.prepareStatement(connection, query, false, parameters);
    }

    /**
     * Prepara una query SQL con opzione per ottenere chiavi generate.
     *
     * @param connection          La connessione al database.
     * @param query               La query SQL da preparare.
     * @param returnGeneratedKeys true se si vogliono restituire
     *                            chiavi generate.
     * @param parameters          I parametri da inserire.
     * @return L'oggetto PreparedStatement pronto per l'esecuzione.
     * @throws SQLException Se si verifica un errore durante la preparazione.
     */
    private PreparedStatement prepareStatement(
            final Connection connection,
            final String query,
            final boolean returnGeneratedKeys,
            final Object[] parameters
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                query, returnGeneratedKeys
                        ? Statement.RETURN_GENERATED_KEYS
                        : Statement.NO_GENERATED_KEYS);
        return this.fillParameters(statement, parameters);
    }

    private List<Object> getGeneratedKeys(
            final PreparedStatement statement
    ) throws SQLException {
        List<Object> keys = new ArrayList<>();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                keys.add(generatedKeys.getObject(1));
            }
        }
        return keys;
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
}