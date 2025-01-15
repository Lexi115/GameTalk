package it.unisa.studenti.nc8.gametalk.business.exceptions;

/**
 * Eccezione lanciata da una classe di servizio in caso di errori
 * (ad esempio problemi con lo storage layer).
 */
public class ServiceException extends Exception {
    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
