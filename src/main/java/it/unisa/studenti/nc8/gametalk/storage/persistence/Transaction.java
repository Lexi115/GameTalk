package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.SQLException;

/**
 * Una transazione SQL, ossia un insieme di operazioni atomiche.
 */
public interface Transaction extends AutoCloseable {
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
    void commit() throws SQLException;

    /**
     * Chiude la transazione, rilasciando eventuali risorse associate.
     * <p>
     * @throws SQLException Se si verifica un errore durante la chiusura
     * della transazione.
     */
    void close() throws SQLException;
}
