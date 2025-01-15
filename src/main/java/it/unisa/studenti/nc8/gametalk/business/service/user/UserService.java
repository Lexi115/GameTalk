package it.unisa.studenti.nc8.gametalk.business.service.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

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
     * @throws ServiceException se si è verificato un errore.
     */
    void createUser(String username, String password) throws ServiceException;

    /**
     * Rimuove un utente esistente.
     *
     * @param id l'ID dell'utente da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    void removeUser(String id) throws ServiceException;

    /**
     * Aggiorna un utente esistente.
     *
     * @param id L'ID dell'utente.
     * @param password La nuova password dell'utente.
     * @throws ServiceException se si è verificato un errore.
     */
    void updateUser(String id, String password) throws ServiceException;

    /**
     * Trova un utente per il suo ID.
     *
     * @param id L'ID dell'utente.
     * @return L'utente con l'ID specificato.
     * @throws ServiceException se si è verificato un errore.
     */
    User findUserById(String id) throws ServiceException;

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

    User findUserByUsername(String username) throws ServiceException;

    /**
     * Trova gli utenti che hanno ricevuto un certo numero di "strikes", con
     * supporto per la paginazione.
     *
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti con "strikes".
     * @throws ServiceException se si è verificato un errore.
     */
    List<User> findStruckUsers(int page, int pageSize)
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
}
