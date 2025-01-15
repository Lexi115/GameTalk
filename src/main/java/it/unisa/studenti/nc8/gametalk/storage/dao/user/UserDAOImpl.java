package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementazione della classe DAO per l'entità {@link User}.
 * Questa classe fornisce metodi per l'interazione con il database
 * per le operazioni CRUD relative all'entità {@link User}.
 * <p>
 * Estende {@link DatabaseDAO<User>} e implementa {@link UserDAO}.
 *
 * @version 1.0
 */
public class UserDAOImpl extends DatabaseDAO<User> implements UserDAO {

    /**
     * Costruttore.
     *
     * @param db     Istanza di {@link Database} per la connessione al database.
     * @param mapper Mapper per convertire un {@link ResultSet}
     *               in un oggetto {@link User}.
     */
    public UserDAOImpl(final Database db, final ResultSetMapper<User> mapper) {
        super(db, mapper);
    }

    /**
     * Recupera un'entità dal sistema di persistenza in base al suo
     * identificativo univoco.
     *
     * @param id l'identificativo univoco dell'entità da recuperare.
     * @return l'entità corrispondente all'ID fornito, o {@code null}
     * se non trovata.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public User get(final String id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM users WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);
            List<User> users = this.getMapper().map(rs);
            return !users.isEmpty() ? users.getFirst() : null;
        } catch (SQLException e) {
            throw new DAOException("Errore recupero utente: ", e);
        }
    }

    /**
     * Recupera tutte le entità del tipo specificato dal sistema di persistenza.
     *
     * @return una lista contenente tutte le entità, oppure una lista vuota se
     * nessuna entità è stata trovata.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public List<User> getAll() throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM users";

            ResultSet rs = db.executeQuery(query);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore recupero utenti: ", e);
        }
    }

    /**
     * Salva una nuova entità nel sistema di persistenza.
     *
     * @param entity l'entità da salvare.
     * @return l'ID della nuova riga aggiunta, o 0 se non è stata
     * aggiunta alcuna riga.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public String save(final User entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "INSERT INTO users (username, password_hash, "
                    + "creation_date, banned, strikes, role) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getCreationDate(),
                    entity.isBanned(),
                    entity.getStrikes(),
                    entity.getRole().toString()
            };

            List<Object> keys = db.executeInsert(query, params);
            return !keys.isEmpty() ? (String) keys.getFirst() : null;
        } catch (SQLException e) {
            throw new DAOException("Errore salvataggio utente: ", e);
        }
    }

    /**
     * Aggiorna i dati di un'entità esistente nel sistema di persistenza.
     *
     * @param entity l'entità da aggiornare.
     * @return <code>true</code> se la riga è stata aggiornata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public boolean update(final User entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "UPDATE users SET "
                    + "password_hash = ?, creation_date = ?, banned = ?, "
                    + "strikes = ?, roles = ? WHERE username = ?";

            Object[] params = {
                    entity.getPassword(),
                    entity.getCreationDate(),
                    entity.isBanned(),
                    entity.getStrikes(),
                    entity.getRole().toString(),
                    entity.getUsername()
            };

            return db.executeUpdate(query, params) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore aggiornamento utente: ", e);
        }
    }

    /**
     * Elimina un'entità dal sistema di persistenza in base al suo
     * identificativo univoco.
     *
     * @param id l'identificativo univoco dell'entità da eliminare.
     * @return <code>true</code> se la riga è stata eliminata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public boolean delete(final String id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "DELETE FROM users WHERE username = ?";

            return db.executeUpdate(query, id) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore rimozione utente: ", e);
        }
    }

    /**
     * Recupera una lista di utenti che hanno uno username corrispondente
     * o simile a quello specificato. Supporta la paginazione.
     *
     * @param username il nome utente da cercare, può includere caratteri jolly.
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti che corrispondono al criterio di ricerca.
     * @throws IllegalArgumentException se <code>page</code> o
     * <code>limit</code> sono minori o uguali a 0.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    @Override
    public List<User> getUsersByUsername(
            final String username,
            final int page,
            final int limit
    ) throws DAOException {
        if (page < 1 || limit < 1) {
            throw new IllegalArgumentException(
                    "Valori page / limit non validi");
        }

        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String query = "SELECT * FROM users WHERE username LIKE ? "
                    + "LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, username, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(
                    "Errore recupero lista utenti per username: ", e);
        }
    }

    public User getUserByUsername(final String username) throws DAOException {
        List<User> users = getUsersByUsername(username, 1, 1);
        return !users.isEmpty() ? users.getFirst() : null;
    }

    /**
     * Recupera una lista di utenti che hanno ricevuto almeno
     * un avvertimento (strike).
     * Supporta la paginazione.
     *
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti che hanno ricevuto almeno uno strike.
     * @throws IllegalArgumentException se <code>page</code> o
     * <code>limit</code> sono minori o uguali a 0.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    @Override
    public List<User> getStruckUsers(
            final int page,
            final int limit
    ) throws DAOException {
        if (page < 1 || limit < 1) {
            throw new IllegalArgumentException(
                    "Valori page / limit non validi");
        }

        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String query = "SELECT * FROM users WHERE strikes > 0 "
                    + "LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore recupero utenti con strikes: ", e);
        }
    }

    /**
     * Recupera una lista di utenti bannati. Supporta la paginazione.
     *
     * @param page il numero della pagina da recuperare (partendo da 1).
     * @param limit il numero massimo di risultati per pagina.
     * @return una lista di utenti bannati.
     * @throws IllegalArgumentException se <code>page</code> o
     * <code>limit</code> sono minori o uguali a 0.
     * @throws DAOException se si verifica un errore durante l'interazione
     * con il database.
     */
    @Override
    public List<User> getBannedUsers(
            final int page,
            final int limit
    ) throws DAOException {
        if (page < 1 || limit < 1) {
            throw new IllegalArgumentException(
                    "Valori page / limit non validi");
        }

        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String query = "SELECT * FROM users WHERE banned = 1 "
                    + "LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore recupero utenti bannati: ", e);
        }
    }
}
