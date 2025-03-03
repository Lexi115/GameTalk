package it.unisa.studenti.nc8.gametalk.business.exceptions;

/**
 * Eccezione lanciata in caso di errori durante il processo di validazione.
 */
public class ValidationException extends IllegalArgumentException {

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public ValidationException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public ValidationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public ValidationException(
            final String message, final Throwable cause) {
        super(message, cause);
    }
}
