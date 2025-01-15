package it.unisa.studenti.nc8.gametalk.storage.exceptions;

/**
 * Eccezione lanciata da una classe che implementa
 * {@link it.unisa.studenti.nc8.gametalk.storage.dao.DAO} in caso di errori
 * (ad esempio problemi con il sistema di persistenza).
 */
public class DAOException extends Exception {
    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public DAOException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public DAOException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public DAOException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
