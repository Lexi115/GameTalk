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
import java.util.List;

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
            throw new DAOException(e.getMessage());
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
            throw new DAOException(e.getMessage());
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
            throw new DAOException(e.getMessage());
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
            throw new DAOException(e.getMessage());
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
            throw new DAOException(e.getMessage());
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
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(
            final String title,
            final int page,
            final int limit,
            final Order order
    ) throws DAOException {
        return this.getThreadsByTitle(title, null, page, limit, order);
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
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(
            final String title,
            final Category category,
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

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ? "
                    + "AND category LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };
            query += " LIMIT ? OFFSET ?";

            String categoryString = category != null
                    ? category.toString() : "%%";
            ResultSet rs = db.executeQuery(query,
                    "%" + title + "%", categoryString, limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
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
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByCategory(
            final Category category,
            final int page,
            final int limit,
            final Order order
    ) throws DAOException {
        int offset = (page - 1) * limit;

        try {
            Database db = this.getDb();
            String baseQuery = "SELECT * FROM threads WHERE category LIKE ?";
            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };
            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(
                    query, category.toString(), limit, offset);
            return this.getMapper().map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
