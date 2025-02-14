package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.SQLException;

public interface Transaction extends AutoCloseable {
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
    void commit() throws SQLException;

    void close() throws SQLException;
}
