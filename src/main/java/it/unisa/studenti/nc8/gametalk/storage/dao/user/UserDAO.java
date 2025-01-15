package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

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

    User getUserByUsername(String username) throws DAOException;

    /**
     * Recupera una lista di utenti che hanno ricevuto almeno
     * un avvertimento (strike).
     * Supporta la paginazione.
     *
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti che hanno ricevuto almeno uno strike.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    List<User> getStruckUsers(int page, int limit) throws DAOException;

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
}
