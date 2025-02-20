package it.unisa.studenti.nc8.gametalk.business.services.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;

import java.util.List;

/**
 * Interfaccia di servizio per la gestione di oggetti {@link User}.
 * Fornisce metodi per creare, rimuovere, aggiornare e cercare utenti.
 */
public interface UserService {

    /**
     * Aggiunge un nuovo utente.
     *
     * @param username L'username dell'utente.
     * @param password La password dell'utente.
     * @throws ServiceException         se si è verificato un errore.
     * @throws IllegalArgumentException se l'username e/o password
     * sono incorretti.
     */
    void createUser(String username, String password) throws ServiceException;

    /**
     * Rimuove un utente esistente.
     *
     * @param username l'ID dell'utente da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    void removeUser(String username) throws ServiceException;

    /**
     * Aggiorna la password di un utente esistente.
     *
     * @param username Lo username dell'utente da aggiornare
     * @param password La nuova password dell'utente.
     * @throws ServiceException se si è verificato un errore.
     * @throws IllegalArgumentException se la password fornita non è valida.
     */
    void updatePassword(
            String username,
            String password
    ) throws ServiceException;

    /**
     * Aggiorna il token di un utente.
     *
     * @param username Lo username dell'utente da aggiornare
     * @param token Il nuovo token dell'utente.
     * @throws ServiceException se si è verificato un errore.
     */
    void updateToken(String username, String token) throws ServiceException;

    /**
     * Trova un utente per il suo ID.
     *
     * @param username L'ID dell'utente.
     * @return L'utente con l'ID specificato o <code>null</code>
     * se non trovato.
     * @throws ServiceException se si è verificato un errore.
     */
    User findUserByUsername(String username) throws ServiceException;

    /**
     * Trova gli utenti per nome utente con supporto per la paginazione.
     *
     * @param username Il nome utente da cercare.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti che corrispondono al nome utente.
     * @throws ServiceException se si è verificato un errore.
     */
    List<User> findUsersByUsername(String username, int page, int pageSize)
            throws ServiceException;

    /**
     * Trova gli utenti bannati, con supporto per la paginazione.
     *
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti bannati.
     * @throws ServiceException se si è verificato un errore.
     */
    List<User> findBannedUsers(int page, int pageSize)
            throws ServiceException;

    /**
     * Banna/Unbanna un utente dato il suo nome utente.
     *
     * @param username il nome utente dell'utente da bannare/unbannare.
     * @param banned Indica come aggiornare lo stato dell'utente.
     * @throws IllegalArgumentException se l'username è <code>null</code>
     * o non valido.
     * @throws ServiceException se si verifica un errore durante l'operazione.
     */
    void banUser(String username, boolean banned)
            throws ServiceException;
}
