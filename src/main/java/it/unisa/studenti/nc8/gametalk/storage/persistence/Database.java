package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per la gestione della connessione e delle operazioni con un database SQL.
 *
 * @version 1.0
 */
public interface Database extends AutoCloseable {
    /**
     * Crea una connessione al database.
     *
     * @throws SQLException Se la connessione fallisce.
     */
    void connect() throws SQLException;

    /**
     * Controlla se la connessione al database è attiva.
     *
     * @return true se la connessione è attiva, false altrimenti.
     */
    boolean isConnected();

    /**
     * Chiude la connessione al database.
     *
     * @throws SQLException Se si verifica un errore durante la chiusura.
     */
    @Override
    void close() throws SQLException;

    /**
     * Esegue una query SELECT sul database.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il risultato della query come ResultSet.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    ResultSet executeQuery(String query, Object... parameters) throws SQLException;

    /**
     * Esegue una query di tipo INSERT, UPDATE o DELETE sul database.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il numero di righe modificate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    int executeUpdate(String query, Object... parameters) throws SQLException;

    /**
     * Esegue una query di tipo INSERT e restituisce le chiavi generate.
     *
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Una lista di tutte le chiavi generate.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    List<Long> executeInsert(String query, Object... parameters) throws SQLException;
}
