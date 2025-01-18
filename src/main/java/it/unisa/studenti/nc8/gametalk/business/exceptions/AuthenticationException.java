package it.unisa.studenti.nc8.gametalk.business.exceptions;

/**
 * Eccezione lanciata in caso di errori durante il processo di autenticazione
 * (ad esempio credenziali non valide).
 */
public class AuthenticationException extends Exception {

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     */
    public AuthenticationException(final String message) {
        super(message);
    }

    /**
     * Costruttore.
     *
     * @param cause La causa del problema
     */
    public AuthenticationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Costruttore.
     *
     * @param message Il messaggio di errore
     * @param cause La causa del problema
     */
    public AuthenticationException(
            final String message, final Throwable cause) {
        super(message, cause);
    }
}
