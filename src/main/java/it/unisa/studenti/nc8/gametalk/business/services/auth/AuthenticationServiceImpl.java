package it.unisa.studenti.nc8.gametalk.business.services.auth;

import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.business.utils.hashing.Hasher;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe di servizio per la gestione dell'autenticazione utente.
 * Fornisce metodi per eseguire il login e generare token di
 * autenticazione.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Il database con il quale lavorare.
     */
    private final Database db;

    /**
     * Il DAO per interagire con gli utenti sul
     * sistema di persistenza.
     */
    private final UserDAO userDAO;

    /** L'hasher della password. */
    private final Hasher passwordHasher;

    /** L'hasher del token di autenticazione. */
    private final Hasher tokenHasher;

    /**
     * Costruttore.
     *
     * @param db      il database utilizzato per la persistenza dei dati.
     * @param userDAO il DAO per gestire gli utenti sul sistema di
     *                persistenza.
     * @param passwordHasher l'hasher della password.
     * @param tokenHasher    l'hasher del token.
     */
    public AuthenticationServiceImpl(
            final Database db,
            final UserDAO userDAO,
            final Hasher passwordHasher,
            final Hasher tokenHasher
    ) {
        this.db = db;
        this.userDAO = userDAO;
        this.passwordHasher = passwordHasher;
        this.tokenHasher = tokenHasher;
    }

    /**
     * Autentica un utente utilizzando il nome utente e la password.
     *
     * @param username il nome utente dell'utente
     * @param password la password associata al nome utente
     * @return l'oggetto {@link User} autenticato se il login ha successo
     * @throws IllegalArgumentException se il nome utente o la password
     *                                  sono nulli o vuoti
     * @throws AuthenticationException  se il login fallisce
     * @throws ServiceException se si verifica un errore
     */
    @Override
    public User login(
            final String username,
            final String password
    ) throws AuthenticationException, ServiceException {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username non valido");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password non valida");
        }

        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            User user = userDAO.get(username);
            if (user == null) {
                throw new AuthenticationException("Utente non trovato");
            }

            // Confronta password con la sua versione hashed sul database
            if (passwordHasher.verify(password, user.getPassword())) {
                throw new AuthenticationException("Password non valida");
            }

            return user;
        } catch (SQLException | DAOException e) {
            throw new ServiceException("Errore durante autenticazione", e);
        }
    }

    /**
     * Autentica un utente utilizzando un token di autenticazione.
     *
     * @param token il token di autenticazione
     * @return l'oggetto {@link User} autenticato se il token è valido
     * @throws IllegalArgumentException se il token è nullo o vuoto
     * @throws AuthenticationException  se il token è invalido
     */
    @Override
    public User loginByToken(final String token)
            throws AuthenticationException, ServiceException {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token non valido");
        }

        try (Connection connection = db.connect()) {
            userDAO.bind(connection);

            //Effettuo il secondo hash del token
            String hashedToken = generateToken(token);
            User user = userDAO.getUserByToken(hashedToken);
            if (user == null) {
                throw new AuthenticationException("Utente non trovato");
            }

            return user;
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante autenticazione con token", e);
        }
    }

    /**
     * Genera un token di autenticazione.
     *
     * @param input La stringa in input.
     * @return Il token generato.
     */
    @Override
    public String generateToken(final String input) {
        return tokenHasher.hash(input);
    }
}
