package it.unisa.studenti.nc8.gametalk.presentation.exceptions;

/**
 * Eccezione lanciata quando mancano i permessi per compiere
 * un'azione o accedere a una risorsa.
 */
public class PermissionException extends Exception {

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public PermissionException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public PermissionException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public PermissionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
