package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.models.user.User;

import java.util.List;

/**
 * Definizione dell'interfaccia DAO per l'entità {@link User}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione
 * con il database per le operazioni CRUD relative all'entità {@link User}.
 * <p>
 * Estende {@link DAO<User>}.
 *
 * @version 1.0
 */
public interface UserDAO extends DAO<User, String> {

    /**
     * Recupera una lista di utenti che hanno uno username corrispondente
     * o simile a quello specificato. Supporta la paginazione.
     *
     * @param username il nome utente da cercare, può includere caratteri jolly.
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti che corrispondono al criterio di ricerca.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    List<User> getUsersByUsername(String username, int page, int limit)
            throws DAOException;

    /**
     * Recupera una lista di utenti bannati. Supporta la paginazione.
     *
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti bannati.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    List<User> getBannedUsers(int page, int limit) throws DAOException;

    /**
     * Recupera un utente dal database utilizzando il token di autenticazione.
     *
     * @param token Il token di autenticazione associato all'utente.
     * @return L'oggetto User corrispondente al token fornito o {@code null}
     * se non esiste alcun utente associato.
     * @throws DAOException Se si verifica un errore durante l'interrogazione
     * del database.
     *
     */
    User getUserByToken(String token) throws DAOException;
}
