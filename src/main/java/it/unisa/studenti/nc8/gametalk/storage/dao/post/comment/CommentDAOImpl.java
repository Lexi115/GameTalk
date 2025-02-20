package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.QueryResult;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.comment.CommentMapper;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDAOImpl extends DatabaseDAO<Comment> implements CommentDAO {

    /**
     * Costruttore.
     *
     * @param db         Il database.
     * @param connection La connessione al database.
     */
    public CommentDAOImpl(final Database db, final Connection connection) {
        super(db, connection, new CommentMapper());
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM comments WHERE id = ?";

        try (QueryResult qr = db.executeQuery(connection, query, id)) {
            List<Comment> comments = this.getMapper().map(qr.getResultSet());
            return !comments.isEmpty() ? comments.getFirst() : null;
        } catch (SQLException e) {
            throw new DAOException("Errore recupero commento ID " + id, e);
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM comments";

        try (QueryResult qr = db.executeQuery(connection, query)) {
            return this.getMapper().map(qr.getResultSet());
        } catch (SQLException e) {
            throw new DAOException("Errore recupero commenti", e);
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "INSERT INTO comments (thread_id, username, body, "
                + "votes, creation_date) VALUES (?, ?, ?, ?, ?)";
        Object[] params = {
                entity.getThreadId(),
                entity.getUsername(),
                entity.getBody(),
                entity.getVotes(),
                entity.getCreationDate()
        };

        try {
            List<Object> keys = db.executeInsert(connection, query, params);
            return !keys.isEmpty()
                    ? ((BigInteger) keys.getFirst()).longValue() : 0;
        } catch (SQLException e) {
            throw new DAOException("Errore salvataggio commento", e);
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "UPDATE comments SET thread_id = ?, username = ?, "
                + "body = ?, votes = ?, creation_date = ? WHERE id = ?";
        Object[] params = {
                entity.getThreadId(),
                entity.getUsername(),
                entity.getBody(),
                entity.getVotes(),
                entity.getCreationDate(),
                entity.getId()
        };

        try {
            return db.executeUpdate(connection, query, params) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore aggiornamento commento", e);
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
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "DELETE FROM comments WHERE id = ?";

        try {
            return db.executeUpdate(connection, query, id) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore rimozione commento", e);
        }
    }

    /**
     * Recupera una lista di commenti in base all'ID del thread,
     * con supporto per la paginazione.
     *
     * @param threadId l'ID del thread per cui recuperare i commenti.
     * @param username Il nome utente del richiedente, vuoto
     *                 se non è loggato.
     * @param page il numero della pagina.
     * @param limit il numero massimo di commenti per pagina.
     * @return una lista di commenti del thread specificato.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query.
     */
    @Override
    public List<Comment> getCommentsByThreadId(
            final long threadId,
            final String username,
            final int page,
            final int limit
    ) throws DAOException {
        int offset = (page - 1) * limit;

        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT * FROM comments WHERE thread_id = ? "
                + "ORDER BY (username = ?) DESC, creation_date DESC "
                + "LIMIT ? OFFSET ?";

        try (QueryResult qr = db.executeQuery(
                connection, query, threadId, username, limit, offset)) {
            return this.getMapper().map(qr.getResultSet());
        } catch (SQLException e) {
            throw new DAOException(
                    "Errore recupero commenti thread ID " + threadId, e);
        }
    }

    /**
     * Conta i commenti di un determinato thread.
     *
     * @param threadId l'ID del thread corrispondente.
     * @return il numero totale di commenti del thread.
     * @throws DAOException se si verifica un errore durante
     *                      l'esecuzione della query
     */
    @Override
    public long countCommentsByThreadId(
            final long threadId) throws DAOException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "SELECT COUNT(id) FROM comments WHERE thread_id = ?";

        try (QueryResult qr = db.executeQuery(connection, query, threadId)) {
            ResultSet rs = qr.getResultSet();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new DAOException(
                    "Errore recupero commenti thread ID " + threadId, e);
        }
    }

    /**
     * Vota un commento associato al suo ID.
     * Se l'utente ha già votato il commento, il voto viene aggiornato con
     * il nuovo valore.
     *
     * @param commentId ID del commento da votare.
     * @param username Nome dell'utente che sta effettuando il voto.
     * @param vote Valore del voto da assegnare al commento. Deve essere:
     *             <ul>
     *             <li>-1: Downvote.</li>
     *             <li>0: Voto neutro o rimozione del voto (se presente).</li>
     *             <li>1: Upvote.</li>
     *             </ul>
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    @Override
    public void voteComment(
            final long commentId,
            final String username,
            final int vote
    ) throws DAOException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query =
                "INSERT INTO votes_comments (username, comment_id, vote)"
                        + " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE vote = ?";

        try {
            db.executeInsert(connection, query, username,
                    commentId, vote, vote);
            updateCommentVotes(commentId);
        } catch (SQLException e) {
            throw new DAOException("Voto non andato a buon fine", e);
        }
    }

    /**
     * Rimuove il voto di un commento associato al suo ID se questo esiste.
     *
     * @param commentId ID del commento di cui rimuovere il voto.
     * @param username Nome dell'utente che ha espresso il voto da rimuovere.
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    @Override
    public void removeVoteComment(
            final long commentId,
            final String username
    ) throws DAOException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String query = "DELETE FROM votes_comments "
                + "WHERE username = ? AND comment_id = ?";

        try {
            if (db.executeUpdate(connection, query, username, commentId) > 0) {
                updateCommentVotes(commentId);
            }
        } catch (SQLException e) {
            throw new DAOException("Rimozione voto non andata a buon fine", e);
        }
    }

    /**
     * Aggiorna il conteggio totale dei voti di un commento effettuando una
     * somma di tutti i voti presenti nel database.
     *
     * @param commentId ID del commento di cui aggiornare il conteggio dei voti.
     *
     * @throws SQLException Se si verifica un errore durante l'aggiornamento del
     * conteggio dei voti nel database.
     */
    private void updateCommentVotes(final long commentId) throws SQLException {
        Database db = this.getDatabase();
        Connection connection = this.getConnection();
        String updateVotesQuery = "UPDATE comments SET votes = "
                + "COALESCE((SELECT SUM(vote) "
                + "FROM votes_comments WHERE comment_id = ?), 0)"
                + " WHERE id = ?";

        db.executeUpdate(connection, updateVotesQuery, commentId, commentId);
    }

    @Override
    public void bind(final Object object) {
        setConnection((Connection) object);
    }
}
