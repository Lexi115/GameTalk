package it.unisa.studenti.nc8.gametalk.storage.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione della connessione e delle operazioni
 * con un database SQL. Utilizza un pool di connessioni per
 * migliorare le prestazioni.
 */
public class DatabaseImpl implements Database {

    /** Logger. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DatabaseImpl.class);

    /** Pool per gestire le connessioni al database. */
    private final DataSource dataSource;

    /**
     * Costruttore.
     *
     * @param dataSource    La data source.
     */
    public DatabaseImpl(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Restituisce una connessione dalla pool.
     * <p>
     * @return Una connessione al database.
     * @throws SQLException Se si verifica un errore durante il
     * recupero della connessione.
     */
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
}
