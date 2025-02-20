package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;

import java.sql.Connection;
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
     * @param db         Il database.
     * @param connection La connessione al database.
     * @param mapper     Il mapper per trasformare il risultato di
     *                   una query in un oggetto {@link User}.
     */
    public UserDAOImpl(
            final Database db,
            final Connection connection,
            final ResultSetMapper<User> mapper
    ) {
        super(db, connection, mapper);
    }

    /**
     * Recupera un'entità dal sistema di persistenza in base al suo
     * identificativo univoco.
     *
     * @param username l'identificativo univoco dell'entità da recuperare.
     * @return l'entità corrispondente all'ID fornito, o {@code null}
     * se non trovata.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    @Override
    public User get(final String username) throws DAOException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        try (QueryResult qr = db.executeQuery(connection, query, username)) {
            List<User> users = this.getMapper().map(qr.getResultSet());
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM users";

        try (QueryResult qr = db.executeQuery(connection, query)) {
            return this.getMapper().map(qr.getResultSet());
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "INSERT INTO users (username, password, "
                + "creation_date, banned, role, auth_token) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Object[] params = {
                entity.getUsername(),
                entity.getPassword(),
                entity.getCreationDate(),
                entity.isBanned(),
                entity.getRole().toString(),
                entity.getAuthToken()
        };

        try {
            List<Object> keys = db.executeInsert(connection, query, params);
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "UPDATE users SET "
                + "password = ?, creation_date = ?, banned = ?, "
                + "role = ?, auth_token = ? WHERE username = ?";

        Object[] params = {
                entity.getPassword(),
                entity.getCreationDate(),
                entity.isBanned(),
                entity.getRole().toString(),
                entity.getAuthToken(),
                entity.getUsername()
        };

        try {
            return db.executeUpdate(connection, query, params) > 0;
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "DELETE FROM users WHERE username = ?";

        try {
            return db.executeUpdate(connection, query, id) > 0;
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
            throw new IllegalArgumentException("Valori page/limit non validi");
        }

        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        int offset = (page - 1) * limit;
        String query = "SELECT * FROM users WHERE username LIKE ? "
                + "LIMIT ? OFFSET ?";

        try (QueryResult qr = db.executeQuery(
                connection, query, username, limit, offset)) {
            return this.getMapper().map(qr.getResultSet());
        } catch (Exception e) {
            throw new DAOException(
                    "Errore recupero lista utenti per username: ", e);
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
            throw new IllegalArgumentException("Valori page/limit non validi");
        }

        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        int offset = (page - 1) * limit;
        String query = "SELECT * FROM users WHERE banned = 1 LIMIT ? OFFSET ?";

        try (QueryResult qs = db.executeQuery(
                connection, query, limit, offset)) {
            return this.getMapper().map(qs.getResultSet());
        } catch (Exception e) {
            throw new DAOException("Errore recupero utenti bannati: ", e);
        }
    }

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
    @Override
    public User getUserByToken(final String token) throws DAOException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM users WHERE auth_token = ?";

        try (QueryResult qs = db.executeQuery(connection, query, token)) {
            List<User> users = this.getMapper().map(qs.getResultSet());
            return !users.isEmpty() ? users.getFirst() : null;
        } catch (Exception e) {
            throw new DAOException("Errore recupero utente con token", e);
        }
    }

    @Override
    public void bind(final Object object) {
        setConnection((Connection) object);
    }
}
