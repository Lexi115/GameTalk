package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Rappresenta il risultato di una query al database.
 */
public interface QueryResult extends AutoCloseable {

    /**
     * Restituisce il {@link ResultSet} associato a questo risultato di query.
     *
     * @return il {@code ResultSet} contenente i risultati della query.
     * @throws SQLException se si verifica un errore SQL
     * durante l'accesso ai risultati.
     */
    ResultSet getResultSet() throws SQLException;

    /**
     * Chiude il risultato della query e rilascia le risorse associate.
     *
     * @throws SQLException se si verifica un errore durante la chiusura.
     */
    @Override
    void close() throws SQLException;
}
