package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per la gestione della connessione e delle operazioni
 * con un database SQL.
 *
 * @version 1.0
 */
public interface Database {

    /**
     * Restituisce una connessione dalla pool.
     * <p>
     * @return Una connessione al database.
     * @throws SQLException Se si verifica un errore durante il
     * recupero della connessione.
     */
    Connection connect() throws SQLException;

    /**
     * Esegue una query SELECT sul database.
     *
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il risultato della query.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    QueryResult executeQuery(
            Connection connection, String query, Object... parameters)
            throws SQLException;

    /**
     * Esegue una query di tipo INSERT, UPDATE o DELETE sul database.
     *
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il numero di righe modificate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    int executeUpdate(
            Connection connection, String query, Object... parameters)
            throws SQLException;

    /**
     * Esegue una query di tipo INSERT e restituisce le chiavi generate.
     *
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Una lista di tutte le chiavi generate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    List<Object> executeInsert(
            Connection connection, String query, Object... parameters)
            throws SQLException;
}
