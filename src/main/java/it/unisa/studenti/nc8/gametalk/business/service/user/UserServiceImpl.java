package it.unisa.studenti.nc8.gametalk.business.service.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.user.UserValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link User}.
 */
public class UserServiceImpl implements UserService {

    /**
     * Il database con il quale lavorare.
     */
    private final Database db;

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link User}.
     */
    private final UserDAO userDAO;

    /**
     * L'oggetto che valida i campi di {@link User}.
     */
    private final Validator<User> userValidator;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public UserServiceImpl(final Database db) {
        this.db = db;
        this.userDAO = new UserDAOImpl(db, new UserMapper());
        this.userValidator = new UserValidator();
    }

    /**
     * Aggiunge un nuovo utente.
     *
     * @param username L'username dell'utente.
     * @param password La password dell'utente.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void createUser(
            final String username,
            final String password
    ) throws ServiceException {
        try (db) {
            db.connect();
            db.beginTransaction();

            // Crea nuovo utente
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setCreationDate(LocalDate.now());

            // Valida username e password
            if (!userValidator.validate(user)) {
                throw new ServiceException("Username o password incorretti");
            }

            // Verifica esistenza di un utente con lo stesso username
            User existingUser = userDAO.get(username);
            if (existingUser != null) {
                throw new ServiceException("Username già in uso.");
            }

            // Salva nuovo utente
            userDAO.save(user);
            db.commit();
        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException e1) {
                throw new ServiceException("Errore rollback", e1);
            }
            throw new ServiceException("Errore creazione utente", e);
        }
    }

    /**
     * Rimuove un utente esistente.
     *
     * @param username l'id dell'utente da rimuovere.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void removeUser(final String username) throws ServiceException {
        try (db) {
            db.connect();
            userDAO.delete(username);
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore rimozione utente", e);
        }
    }

    /**
     * Aggiorna un utente esistente.
     *
     * @param username L'ID dell'utente.
     * @param password La nuova password dell'utente.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void updatePassword(
            final String username,
            final String password
    ) throws ServiceException {
        try (db) {
            db.connect();
            db.beginTransaction();

            // Trova utente già esistente
            User user = userDAO.get(username);
            if (user == null) {
                throw new ServiceException("Utente non trovato");
            }

            // Aggiorna campi utente
            user.setPassword(password);

            // Valida password
            if (!userValidator.validate(user)) {
                throw new ServiceException("Password non valida");
            }

            // Aggiorna utente
            userDAO.update(user);
            db.commit();
        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException e1) {
                throw new ServiceException("Errore rollback", e1);
            }
            throw new ServiceException("Errore aggiornamento utente", e);
        }
    }

    /**
     * Aggiorna un utente esistente.
     *
     * @param username L'ID dell'utente.
     * @param token Il nuovo token dell'utente.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void updateToken(
            final String username,
            final String token
    ) throws ServiceException {
        try (db) {
            db.connect();
            db.beginTransaction();

            // Trova utente già esistente
            User user = userDAO.get(username);
            if (user == null) {
                throw new ServiceException("Utente non trovato");
            }

            // Aggiorna campi utente
            user.setAuthToken(token);

            // Aggiorna utente
            userDAO.update(user);
            db.commit();
        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException ex) {
                throw new ServiceException("Errore rollback", ex);
            }
            throw new ServiceException("Errore aggiornamento utente", e);
        }
    }

    /**
     * Trova un utente per il suo ID.
     *
     * @param username L'ID dell'utente.
     * @return L'utente con l'ID specificato.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public User findUserByUsername(final String username)
            throws ServiceException {
        List<User> users = this.findUsersByUsername(username, 1, 1);
        return !users.isEmpty() ? users.getFirst() : null;
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
        try (db) {
            db.connect();
            return userDAO.getUsersByUsername(username, page, pageSize);
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore recupero utenti", e);
        }
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
        try (db) {
            db.connect();
            return userDAO.getBannedUsers(page, pageSize);
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore recupero utenti bannati", e);
        }
    }

    /**
     * Banna/Unbanna un utente dato il suo nome utente.
     *
     * @param username il nome utente dell'utente da bannare/unbannare.
     * @param banned Indica come aggiornare lo stato dell'utente.
     * @throws ServiceException se si verifica un errore durante l'operazione.
     */
    @Override
    public void banUser(
            final String username,
            final boolean banned
    ) throws ServiceException {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username non valido");
        }

        try (db) {
            db.connect();
            db.beginTransaction();

            User user = userDAO.get(username);
            if (user == null) {
                throw new ServiceException("Utente non trovato");
            }

            user.setBanned(banned);
            userDAO.update(user);
            db.commit();
        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException ex) {
                throw new ServiceException("Errore rollback", ex);
            }
            throw new ServiceException("Errore aggiornamento utente", e);
        }
    }
}
