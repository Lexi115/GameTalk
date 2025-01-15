package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDAOImpl extends DatabaseDAO<Comment> implements CommentDAO {

    /**
     * Costruttore che inizializza l'oggetto {@link CommentDAOImpl} con
     * il database e il mapper per mappare i risultati delle query.
     *
     * @param db Oggetto {@link Database}, contiene informazioni sul database
     *           con cui interagire.
     * @param mapper il mapper per mappare i risultati del {@link ResultSet}
     */
    public CommentDAOImpl(
            final Database db,
            final ResultSetMapper<Comment> mapper
    ) {
        super(db, mapper);
    }

    /**
     * Recupera un commento specifico dal database in base al suo ID.
     *
     * @param id l'ID del commento da recuperare
     * @return l'oggetto {@link Comment} corrispondente all'ID,
     * oppure {@code null} se non esiste
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    @Override
    public Comment get(final Long id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM comments WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);
            List<Comment> comments = this.getMapper().map(rs);
            return !comments.isEmpty() ? comments.getFirst() : null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Recupera tutti i commenti dal database (pericolosa).
     *
     * @return una lista di tutti i commenti
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    @Override
    public List<Comment> getAll() throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM comments";

            ResultSet rs = db.executeQuery(query);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Salva un nuovo commento nel database.
     *
     * @param entity il commento da salvare
     * @return l'ID della nuova riga aggiunta, o 0 se non è stata
     * aggiunta alcuna riga.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    @Override
    public Long save(final Comment entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "INSERT INTO comments (thread_id, user_id, body, "
                    + "votes, creation_date) VALUES (?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getThreadId(),
                    entity.getUsername(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.getCreationDate()
            };

            List<Object> keys = db.executeInsert(query, params);
            return !keys.isEmpty() ? (Long) keys.getFirst() : 0;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Aggiorna un commento esistente nel database.
     *
     * @param entity il commento da aggiornare
     * @return <code>true</code> se la riga è stata eliminata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    @Override
    public boolean update(final Comment entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "UPDATE comments SET thread_id = ?, user_id = ?, "
                    + "body = ?, votes = ?, creation_date = ? WHERE id = ?";

            Object[] params = {
                    entity.getThreadId(),
                    entity.getUsername(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.getCreationDate(),
                    entity.getId()
            };

            return db.executeUpdate(query, params) > 0;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Elimina un commento dal database.
     *
     * @param id l'ID del commento da eliminare
     * @return <code>true</code> se la riga è stata eliminata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    @Override
    public boolean delete(final Long id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "DELETE FROM comments WHERE id = ?";

            return db.executeUpdate(query) > 0;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Recupera una lista di commenti in base all'ID del thread,
     * con supporto per la paginazione.
     *
     * @param threadId l'ID del thread per cui recuperare i commenti.
     * @param page il numero della pagina.
     * @param limit il numero massimo di commenti per pagina.
     * @return una lista di commenti del thread specificato.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query.
     */
    @Override
    public List<Comment> getCommentsByThreadId(
            final long threadId,
            final int page,
            final int limit
    ) throws DAOException {
        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String query = "SELECT * FROM comments WHERE thread_id = ? "
                    + "ORDER BY creation_date DESC LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, threadId, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
