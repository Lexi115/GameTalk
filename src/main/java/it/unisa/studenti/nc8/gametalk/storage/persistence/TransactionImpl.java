package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Una transazione SQL, ossia un insieme di operazioni atomiche.
 */
public class TransactionImpl implements Transaction {

    /** La connessione al database. */
    private final Connection connection;

    /** Flag per fare controlli sull'avvenuto commit. */
    private boolean committed = false;

    /**
     * Costruttore. Avvia in automatico la transazione.
     * <p>
     * @param connection La connessione al database.
     * @throws SQLException Se si verifica un errore durante l'avvio
     * della transazione.
     */
    public TransactionImpl(
            final Connection connection
    ) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
    }

    /**
     * Conferma la transazione corrente.
     * <p>
     * Questo metodo applica in modo permanente tutte le operazioni eseguite
     * durante la transazione. Dopo il commit, le modifiche non possono
     * essere annullate.
     *
     * @throws SQLException Se si verifica un errore durante
     * il commit della transazione.
     */
    @Override
    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }

    /**
     * Chiude la transazione, rilasciando eventuali risorse associate.
     * Se il commit non è avvenuto, effettua il rollback in automatico.
     * <p>
     * @throws SQLException Se si verifica un errore durante la chiusura
     * della transazione.
     */
    @Override
    public void close() throws SQLException {
        if (!committed) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }
}
