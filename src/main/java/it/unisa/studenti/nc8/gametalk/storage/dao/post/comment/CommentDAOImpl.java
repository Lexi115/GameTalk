package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;

import java.math.BigInteger;
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
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM comments";

            ResultSet rs = db.executeQuery(query);
            return this.getMapper().map(rs);
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
        try {
            Database db = this.getDb();
            String query = "INSERT INTO comments (thread_id, username, body, "
                    + "votes, creation_date) VALUES (?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getThreadId(),
                    entity.getUsername(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.getCreationDate()
            };

            List<Object> keys = db.executeInsert(query, params);
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
        try {
            Database db = this.getDb();
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

            return db.executeUpdate(query, params) > 0;
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
        try {
            Database db = this.getDb();
            String query = "DELETE FROM comments WHERE id = ?";

            return db.executeUpdate(query, id) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore rimozione commento", e);
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
        try {
            Database db = this.getDb();
            String query = "SELECT COUNT(id) FROM comments WHERE thread_id = ?";

            ResultSet rs = db.executeQuery(query, threadId);
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
        try {
            Database db = this.getDb();
            String query =
                    "INSERT INTO votes_comments (username, comment_id, vote)"
                    + " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE vote = ?";

            db.executeInsert(query, username, commentId, vote, vote);

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
        try {
            Database db = this.getDb();
            String query = "DELETE FROM votes_comments "
                    + "WHERE username = ? AND comment_id = ?";

            if (db.executeUpdate(query, username, commentId) > 0) {
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
        Database db = this.getDb();
        String updateVotesQuery = "UPDATE comments SET votes = "
                + "COALESCE((SELECT SUM(vote) "
                + "FROM votes_comments WHERE comment_id = ?), 0)"
                + " WHERE id = ?";

        db.executeUpdate(updateVotesQuery, commentId, commentId);
    }
}
