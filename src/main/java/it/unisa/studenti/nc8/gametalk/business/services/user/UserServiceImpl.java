package it.unisa.studenti.nc8.gametalk.business.services.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.business.exceptions.NotFoundException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Transaction;
import it.unisa.studenti.nc8.gametalk.storage.persistence.TransactionImpl;
import it.unisa.studenti.nc8.gametalk.business.utils.hashing.Hasher;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link User}.
 * Fornisce metodi per creare, rimuovere, aggiornare e cercare utenti.
 */
public class UserServiceImpl implements UserService {

    /**
     * Il database con il quale lavorare.
     */
    private final Database db;

    /**
     * Il DAO per interagire con gli utenti sul
     * sistema di persistenza.
     */
    private final UserDAO userDAO;

    /**
     * L'oggetto che valida i campi di {@link User}.
     */
    private final Validator<User> userValidator;

    /** L'hasher della password. */
    private final Hasher passwordHasher;

    /**
     * Costruttore.
     *
     * @param db                il database utilizzato per la persistenza
     *                          dei dati.
     * @param userDAO           il DAO per gestire gli utenti sul sistema di
     *                          persistenza.
     * @param userValidator     il validator di utenti.
     * @param passwordHasher    l'hasher della password.
     */
    public UserServiceImpl(
            final Database db,
            final UserDAO userDAO,
            final Validator<User> userValidator,
            final Hasher passwordHasher
    ) {
        this.db = db;
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Aggiunge un nuovo utente.
     *
     * @param username L'username dell'utente.
     * @param password La password dell'utente.
     * @throws ServiceException se si è verificato un errore.
     * @throws IllegalArgumentException se l'username e/o password
     * sono incorretti.
     */
    @Override
    public void createUser(
            final String username,
            final String password
    ) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                // Crea nuovo utente
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setCreationDate(LocalDate.now());
                user.setBanned(false);
                user.setRole(Role.Member);

                // Valida username e password
                userValidator.validate(user);

                // Verifica esistenza di un utente con lo stesso username
                User existingUser = userDAO.get(username);
                if (existingUser != null) {
                    throw new IllegalArgumentException("Username già in uso.");
                }

                // Hash della password
                user.setPassword(passwordHasher.hash(password));

                // Salva nuovo utente
                userDAO.save(user);
                tx.commit();
            }
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore creazione utente", e);
        }
    }

    /**
     * Rimuove un utente esistente.
     *
     * @param username l'id dell'utente da rimuovere.
     * @throws ServiceException se si è verificato un errore.
     * @throws NotFoundException se l'utente non è stato trovato.
     */
    @Override
    public void removeUser(final String username) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                if (!userDAO.delete(username)) {
                    throw new NotFoundException("Utente non trovato");
                }
                tx.commit();
            }
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
     * @throws IllegalArgumentException se la password fornita non è valida.
     * @throws NotFoundException se l'utente non è stato trovato.
     */
    @Override
    public void updatePassword(
            final String username,
            final String password
    ) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                User user = userDAO.get(username);
                if (user == null) {
                    throw new NotFoundException("Utente non trovato");
                }

                user.setPassword(password);
                userValidator.validate(user);
                user.setPassword(passwordHasher.hash(password));
                userDAO.update(user);
                tx.commit();
            }
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore aggiornamento password", e);
        }
    }

    /**
     * Aggiorna un utente esistente.
     *
     * @param username L'ID dell'utente.
     * @param token Il nuovo token dell'utente.
     * @throws ServiceException se si è verificato un errore.
     * @throws NotFoundException se l'utente non è stato trovato.
     */
    @Override
    public void updateToken(
            final String username,
            final String token
    ) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                User user = userDAO.get(username);
                if (user == null) {
                    throw new NotFoundException("Utente non trovato");
                }

                user.setAuthToken(token);
                userDAO.update(user);
                tx.commit();
            }
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore aggiornamento token", e);
        }
    }


    /**
     * Trova un utente per il suo ID.
     *
     * @param username L'ID dell'utente.
     * @return L'utente con l'ID specificato o <code>null</code>
     * se non trovato.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public User findUserByUsername(
            final String username
    ) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);
            return userDAO.get(username);
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore recupero utente", e);
        }
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
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);
            return userDAO.getUsersByUsername(username, page, pageSize);
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore ricerca utenti", e);
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
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);
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
     * @throws IllegalArgumentException se l'username è <code>null</code>
     * o non valido.
     * @throws ServiceException se si verifica un errore durante l'operazione.
     * @throws NotFoundException se non è stato trovato nessun utente.
     */
    @Override
    public void banUser(
            final String username,
            final boolean banned
    ) throws ServiceException {
        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                User user = userDAO.get(username);
                if (user == null) {
                    throw new NotFoundException("Utente non trovato");
                }

                user.setBanned(banned);
                userDAO.update(user);
                tx.commit();
            }
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore ban/unban utente", e);
        }
    }
}
