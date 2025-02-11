package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per la gestione della connessione e delle operazioni
 * con un database SQL.
 *
 * @version 1.0
 */
public interface Database {

    Connection getConnection() throws SQLException;

    /**
     * Esegue una query SELECT sul database.
     *
     * @param connection La connessione al database.
     * @param query      La query SQL da eseguire.
     * @param parameters Parametri da inserire nella query.
     * @return Il risultato della query come ResultSet.
     * @throws SQLException Se si verifica un errore durante l'esecuzione.
     */
    ResultSet executeQuery(
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

    /**
     * Avvia una nuova transazione.
     * <p>
     * Questo metodo prepara il contesto per eseguire una serie di operazioni
     * come un'unica unità di lavoro. Le modifiche non saranno persistenti
     * fino a quando non viene eseguito il commit.
     *
     * @param connection    La connessione al database.
     * @throws SQLException Se si verifica un errore durante
     * l'avvio della transazione.
     */
    void beginTransaction(Connection connection) throws SQLException;

    /**
     * Conferma la transazione corrente.
     * <p>
     * Questo metodo applica in modo permanente tutte le operazioni eseguite
     * durante la transazione corrente. Dopo il commit, le modifiche non possono
     * essere annullate.
     *
     * @param connection    La connessione al database.
     * @throws SQLException Se si verifica un errore durante
     * il commit della transazione.
     */
    void commit(Connection connection) throws SQLException;

    /**
     * Annulla la transazione corrente.
     * <p>
     * Questo metodo annulla tutte le operazioni eseguite durante la transazione
     * corrente, ripristinando lo stato precedente.
     *
     * @param connection    La connessione al database.
     * @throws SQLException Se si verifica un errore durante il rollback
     * della transazione.
     */
    void rollback(Connection connection) throws SQLException;
}
