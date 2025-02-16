package it.unisa.studenti.nc8.gametalk.business.services.auth;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe di servizio per la gestione dell'autenticazione utente.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Il database con il quale lavorare.
     */
    private final Database db;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public AuthenticationServiceImpl(final Database db) {
        this.db = db;
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
            UserDAO userDAO = new UserDAOImpl(db, connection);

            User user = userDAO.get(username);
            if (user == null) {
                throw new AuthenticationException("Utente non trovato");
            }

            // Confronta password con la sua versione hashed sul database
            String hashedPassword = Functions.hash(password);
            if (!hashedPassword.equals(user.getPassword())) {
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
            UserDAO userDAO = new UserDAOImpl(db, connection);

            //Effettuo il secondo hash del token
            String hashedToken = Functions.hash(token);
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
}
