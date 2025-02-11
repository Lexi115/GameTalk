package it.unisa.studenti.nc8.gametalk.business.services.auth;

import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.models.user.User;

/**
 * Interfaccia per la gestione dell'autenticazione utente.
 */
public interface AuthenticationService {

    /**
     * Autentica un utente utilizzando il nome utente e la password.
     *
     * @param username il nome utente dell'utente
     * @param password la password associata al nome utente
     * @return l'oggetto {@link User} autenticato se il login ha successo
     * @throws IllegalArgumentException se il nome utente o la password
     * sono nulli o vuoti
     * @throws AuthenticationException se il login fallisce
     * @throws ServiceException se si verifica un errore
     */
    User login(String username, String password)
            throws AuthenticationException, ServiceException;

    /**
     * Autentica un utente utilizzando un token di autenticazione.
     *
     * @param token il token di autenticazione
     * @return l'oggetto {@link User} autenticato se il token è valido
     * @throws IllegalArgumentException se il token è nullo o vuoto
     * @throws AuthenticationException se il token è invalido o scaduto
     * @throws ServiceException se si verifica un errore
     */
    User loginByToken(String token)
            throws AuthenticationException, ServiceException;
}
