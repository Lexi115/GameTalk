package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Rappresenta il risultato di una query al database.
 */
public class QueryResultImpl implements QueryResult {

    /** Il {@link ResultSet} corrispondente. */
    private final ResultSet resultSet;

    /** Lo {@link Statement} che ha generato il {@link ResultSet}. */
    private final Statement statement;

    /**
     * Costruttore.
     *
     * @param resultSet Il result set.
     * @param statement Lo statement del result set.
     */
    public QueryResultImpl(
            final ResultSet resultSet,
            final Statement statement
    ) {
        this.resultSet = resultSet;
        this.statement = statement;
    }

    /**
     * Restituisce il {@link ResultSet} associato a questo risultato di query.
     *
     * @return il {@code ResultSet} contenente i risultati della query.
     * @throws SQLException se si verifica un errore SQL
     * durante l'accesso ai risultati.
     */
    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Chiude il risultato della query e rilascia le risorse associate.
     *
     * @throws SQLException se si verifica un errore durante la chiusura.
     */
    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}
