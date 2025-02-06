package it.unisa.studenti.nc8.gametalk.presentation.exceptions;

/**
 * Eccezione lanciata quando una risorsa
 * non viene trovata.
 */
public class NotFoundException extends Exception {
    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public NotFoundException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
