package it.unisa.studenti.nc8.gametalk.storage.dao.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Implementazione della classe DAO per l'entità Thread.
 * Questa classe fornisce metodi per l'interazione con il database
 * per le operazioni CRUD relative all'entità Thread.
 * <p>
 * Estende {@link DatabaseDAO} e implementa {@link ThreadDAO}.
 *
 * @version 1.0
 */
public class ThreadDAOImpl extends DatabaseDAO<Thread> implements ThreadDAO {

    /**
     * Costruttore.
     *
     * @param db     Istanza di {@link Database} per la connessione al database.
     * @param mapper Mapper per convertire un {@link ResultSet}
     *               in un oggetto {@link Thread}.
     */
    public ThreadDAOImpl(
            final Database db,
            final ResultSetMapper<Thread> mapper
    ) {
        super(db, mapper);
    }

    /**
     * Ottiene un thread specifico dal database dato il suo ID.
     *
     * @param id ID del thread.
     * @return Il thread corrispondente all'ID.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public Thread get(final Long id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM threads WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);
            List<Thread> threads = this.getMapper().map(rs);
            return (!threads.isEmpty() ? threads.getFirst() : null);
        } catch (SQLException e) {
            throw new DAOException("Errore recupero thread ID " + id, e);
        }
    }

    /**
     * Ottiene tutti i thread presenti nel database.
     *
     * @return Una lista di tutti i thread.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getAll() throws DAOException {
        try {
            Database db = this.getDb();
            String query = "SELECT * FROM threads";

            ResultSet rs = db.executeQuery(query);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore recupero threads", e);
        }
    }

    /**
     * Salva un nuovo thread nel database.
     *
     * @param entity Il thread da salvare.
     * @return l'ID della nuova riga aggiunta, o 0 se non è stata
     * aggiunta alcuna riga.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public Long save(final Thread entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "INSERT INTO threads (username, title, body, votes, "
                    + "archived, category, creation_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getUsername(),
                    entity.getTitle(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.isArchived(),
                    entity.getCategory().toString(),
                    entity.getCreationDate()
            };

            List<Object> keys = db.executeInsert(query, params);

            return !keys.isEmpty()
                    ? ((BigInteger) keys.getFirst()).longValue() : 0;

        } catch (SQLException e) {
            throw new DAOException("Errore salvataggio thread", e);
        }
    }

    /**
     * Aggiorna un thread esistente nel database.
     *
     * @param entity Il thread con i dati aggiornati.
     * @return <code>true</code> se la riga è stata aggiornata,
     * <code>false</code> altrimenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public boolean update(final Thread entity) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "UPDATE threads SET username = ?, title = ?, "
                    + "body = ?, votes = ?, archived = ?, category = ? "
                    + "WHERE id = ?";

            Object[] params = {
                    entity.getUsername(),
                    entity.getTitle(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.isArchived(),
                    entity.getCategory().toString(),
                    entity.getId()
            };

            return db.executeUpdate(query, params) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore aggiornamento thread", e);
        }
    }

    /**
     * Cancella un thread dal database dato il suo ID.
     *
     * @param id ID del thread da cancellare.
     * @return <code>true</code> se la riga è stata eliminata,
     * <code>false</code> altrimenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public boolean delete(final Long id) throws DAOException {
        try {
            Database db = this.getDb();
            String query = "DELETE FROM threads WHERE id = ?";

            return db.executeUpdate(query, id) > 0;
        } catch (SQLException e) {
            throw new DAOException("Errore rimozione thread", e);
        }
    }

    /**
     * Ottiene una lista di thread che corrispondono al titolo
     * immesso o parte di esso.
     *
     * @param title Titolo o parte di esso da cercare.
     * @param page  Numero della pagina.
     * @param limit Numero di Thread massimi per pagina.
     * @param order Ordinamento della lista (più votati,
     *              più recenti, più vecchi).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate La data di fine da cui cercare thread, può
     *                essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(
            final String title,
            final int page,
            final int limit,
            final Order order,
            final LocalDate startDate,
            final LocalDate endDate
    ) throws DAOException {
        return this.getThreadsByTitle(
                title,
                null,
                page,
                limit,
                order,
                startDate,
                endDate
        );
    }

    /**
     * Ottiene una lista di thread corrispondenti al titolo e alla categoria.
     *
     * @param title     Titolo o parte di esso da cercare.
     * @param category  Categoria del thread.
     * @param page      Numero della pagina.
     * @param limit     Numero di thread massimi per pagina.
     * @param order     Ordinamento della lista (più votati,
     *                  più recenti, più vecchi).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate La data di fine da cui cercare thread, può
     *                essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(
            final String title,
            final Category category,
            final int page,
            final int limit,
            final Order order,
            final LocalDate startDate,
            final LocalDate endDate
    ) throws DAOException {

        if (page < 1 || limit < 1) {
            throw new IllegalArgumentException(
                    "Valori page / limit non validi");
        }

        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ? "
                    + "AND category LIKE ?";

            String query = composeQuery(baseQuery, order);

            String categoryString = category != null
                    ? category.toString() : "%%";

            ResultSet rs = db.executeQuery(
                    query,
                    "%" + title + "%",
                    categoryString,
                    startDate,
                    endDate,
                    limit,
                    offset
            );

            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore ricerca threads", e);
        }
    }

    /**
     * Ottiene una lista di thread filtrati per categoria.
     *
     * @param category  Categoria del thread.
     * @param page      Numero della pagina (paginazione).
     * @param limit     Numero di Thread massimi per pagina.
     * @param order     Ordinamento della lista (più recenti,
     *                  più vecchi, più votati).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate La data di fine da cui cercare thread, può
     *                essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByCategory(
            final Category category,
            final int page,
            final int limit,
            final Order order,
            final LocalDate startDate,
            final LocalDate endDate
    ) throws DAOException {
        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String baseQuery = "SELECT * FROM threads WHERE category LIKE ?";

            String query = composeQuery(baseQuery, order);

            ResultSet rs = db.executeQuery(
                    query,
                    category.toString(),
                    startDate,
                    endDate,
                    limit,
                    offset
            );

            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException("Errore ricerca threads", e);
        }
    }

    /**
     * Ottiene una lista di thread pubblicati da un utente specifico.
     *
     * @param username  Il nome dell'utente.
     * @param page      Numero della pagina (paginazione).
     * @param limit     Numero di Thread massimi per pagina.
     * @param order     Ordinamento della lista (più recenti,
     *                  più vecchi, più votati).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByUsername(
            final String username,
            final int page,
            final int limit,
            final Order order
    ) throws DAOException {
        if (page < 1 || limit < 1) {
            throw new IllegalArgumentException(
                    "Valori page / limit non validi");
        }

        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();

            String baseQuery = "SELECT * FROM threads WHERE username = ?";

            //Compongo la query con ordine e paginazione
            String query = composeQuery(baseQuery, order);

            ResultSet rs = db.executeQuery(
                    query, username, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Vota un thread associato al suo ID.
     * Se l'utente ha già votato il thread, il voto viene aggiornato con
     * il nuovo valore.
     *
     * @param threadId ID del thread da votare.
     * @param username Nome dell'utente che sta effettuando il voto.
     * @param vote Valore del voto da assegnare al thread. Deve essere:
     *             <ul>
     *             <li>-1: Downvote.</li>
     *             <li>0: Voto neutro o rimozione del voto (se presente).</li>
     *             <li>1: Upovote.</li>
     *             </ul>
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    @Override
    public void voteThread(
            final long threadId,
            final String username,
            final int vote
    ) throws DAOException {
        try {
            Database db = this.getDb();

            String query =
                    "INSERT INTO votes_threads (username, thread_id, vote)"
                    + " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE vote = ?";

            db.executeInsert(query, username, threadId, vote, vote);

            updateThreadVotes(threadId);

        } catch (SQLException e) {
            throw new DAOException("Voto non andato a buon fine", e);
        }
    }

    /**
     * Rimuove il voto di un thread associato al suo ID se questo esiste.
     *
     * @param threadId ID del thread di cui rimuovere il voto.
     * @param username Nome dell'utente che ha espresso il voto da rimuovere.
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    public void removeVoteThread(
            final long threadId,
            final String username
    ) throws DAOException {
        try {
            Database db = this.getDb();

            String query = "DELETE FROM votes_threads "
                    + "WHERE username = ? AND thread_id = ?";

            if (db.executeUpdate(query, username, threadId) > 0) {
                updateThreadVotes(threadId);
            }

        } catch (SQLException e) {
            throw new DAOException("Rimozione voto non andata a buon fine", e);
        }
    }

    /**
     * Aggiorna il conteggio totale dei voti di un thread effettuando una
     * somma di tutti i voti presenti nel database.
     *
     * @param threadId ID del thread di cui aggiornare il conteggio dei voti.
     *
     * @throws SQLException Se si verifica un errore durante l'aggiornamento del
     * conteggio dei voti nel database.
     */
    private void updateThreadVotes(final long threadId) throws SQLException {
        Database db = this.getDb();
        String updateVotesQuery = "UPDATE threads SET votes = "
        + "COALESCE((SELECT SUM(vote) "
        + "FROM votes_threads WHERE thread_id = ?), 0)"
        + " WHERE id = ?";

        db.executeUpdate(updateVotesQuery, threadId, threadId);
    }

    /**
     * Compone una stringa di query SQL basata sulla query di base e
     * sui criteri di ordinamento specificati,
     * aggiungendoci una clausola LIMIT e OFFSET.
     * @param baseQuery la query SQL di base
     *                  (senza clausole di ordinamento, LIMIT od OFFSET).
     * @param order il criterio di ordinamento desiderato
     *              ({@link Order#Oldest}, {@link Order#Newest},
     *              o {@link Order#Best}).
     *              Se nullo, verrà utilizzato {@link Order#Best}.
     * @return la query SQL completa con le clausole ORDER BY,
     * LIMIT e OFFSET aggiunte.
     *
     */

    private String composeQuery(
            final String baseQuery,
            final Order order
    ) {
        //Caso order = null, defaulta a best
        Order realOrder = Objects.requireNonNullElse(order, Order.Best);

        //Concateno condizioni di data
        String finalQuery = baseQuery + " AND creation_date BETWEEN ? AND ?";
        //Concatenazione oder by in base all'ordine scelto (default best)
        finalQuery += switch (realOrder) {
            case Oldest -> " ORDER BY creation_date ASC";
            case Newest -> " ORDER BY creation_date DESC";
            default -> " ORDER BY votes DESC";
        };
        //Limit e offset
        finalQuery += " LIMIT ? OFFSET ?";

        return finalQuery;
    }
}
