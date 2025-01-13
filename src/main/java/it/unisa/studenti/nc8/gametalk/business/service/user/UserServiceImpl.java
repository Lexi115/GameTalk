package it.unisa.studenti.nc8.gametalk.business.service.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;

import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link User}.
 */
public class UserServiceImpl implements UserService {

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link User}.
     */
    private final UserDAO userDAO;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public UserServiceImpl(final Database db) {
        this.userDAO = new UserDAOImpl(db, new UserMapper());
    }

    /**
     * Aggiunge un nuovo utente.
     *
     * @param user L'utente da aggiungere.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void addUser(final User user) throws ServiceException {
        return;
    }

    /**
     * Rimuove un utente esistente.
     *
     * @param id l'id dell'utente da rimuovere.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void removeUser(final long id) throws ServiceException {
        return;
    }

    /**
     * Aggiorna un utente esistente.
     *
     * @param user L'utente da aggiornare.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void updateUser(final User user) throws ServiceException {
        return;
    }

    /**
     * Trova un utente per il suo ID.
     *
     * @param id L'ID dell'utente.
     * @return L'utente con l'ID specificato.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public User findUserById(final long id) throws ServiceException {
        return null;
    }

    /**
     * Trova gli utenti per nome utente con supporto per la paginazione.
     *
     * @param username Il nome utente da cercare. Può essere anche
     *                 incompleto.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti che corrispondono al nome utente.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public List<User> findUsersByUsername(
            final String username,
            final int page,
            final int pageSize
    ) throws ServiceException {
        return List.of();
    }

    /**
     * Trova gli utenti che hanno ricevuto un certo numero di "strikes", con
     * supporto per la paginazione.
     *
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti con "strikes".
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public List<User> findStruckUsers(
            final int page,
            final int pageSize
    ) throws ServiceException {
        return List.of();
    }

    /**
     * Trova gli utenti bannati, con supporto per la paginazione.
     *
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di risultati per pagina.
     * @return Una lista di utenti bannati.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public List<User> findBannedUsers(
            final int page,
            final int pageSize
    ) throws ServiceException {
        return List.of();
    }
}
