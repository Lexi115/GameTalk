package it.unisa.studenti.nc8.gametalk.storage.dao.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Implementazione della classe DAO per l'entità Thread.
 * Questa classe fornisce metodi per l'interazione con il database
 * per le operazioni CRUD relative all'entità Thread.
 * <p>
 * Estende {@link DatabaseDAO} ed implementa {@link ThreadDAO}.
 *
 * @version 1.0
 */
public class ThreadDAOImpl extends DatabaseDAO<Thread> implements ThreadDAO {

    /**
     * Costruttore per la classe ThreadDAOImpl.
     *
     * @param db     Istanza di {@link Database} per la connessione al database.
     * @param mapper Mapper per convertire un {@link ResultSet} in un oggetto {@link Thread}.
     */
    public ThreadDAOImpl(Database db, ResultSetMapper<Thread> mapper) {
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
    public Thread get(long id) throws DAOException {
        try (db){
            db.connect();

            String query = "SELECT * FROM threads WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);

            List<Thread> threads = mapper.map(rs);

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
        try (db){
            db.connect();

            String query = "SELECT * FROM threads";

            ResultSet rs = db.executeQuery(query);
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Salva un nuovo thread nel database.
     *
     * @param entity Il thread da salvare.
     * @return l'ID della nuova riga aggiunta, o 0 se non è stata aggiunta alcuna riga.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public long save(Thread entity) throws DAOException {
        int rowsAffected;
        try (db){
            db.connect();
            String query = "INSERT INTO threads (user_id, title, body, votes, archived, category, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getUserId(),
                    entity.getTitle(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.isArchived(),
                    entity.getCategory().toString(),
                    entity.getCreationDate()
            };

            rowsAffected = db.executeUpdate(query, params);

            return rowsAffected;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Aggiorna un thread esistente nel database.
     *
     * @param entity Il thread con i dati aggiornati.
     * @return <code>true</code> se la riga è stata aggiornata, <code>false</code> altrimenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public boolean update(Thread entity) throws DAOException {
        int rowsAffected;
        try (db){
            db.connect();
            String query = "UPDATE threads SET user_id = ?, title = ?, body = ?, votes = ?, archived = ?, category = ?, creation_date = ? WHERE id = ?";

            Object[] params = {
                    entity.getUserId(),
                    entity.getTitle(),
                    entity.getBody(),
                    entity.getVotes(),
                    entity.isArchived(),
                    entity.getCategory().toString(),
                    entity.getCreationDate(),
                    entity.getId()
            };

            rowsAffected = db.executeUpdate(query, params);
            return (rowsAffected == 1);

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Cancella un thread dal database dato il suo ID.
     *
     * @param id ID del thread da cancellare.
     * @return <code>true</code> se la riga è stata aggiornata, <code>false</code> altrimenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public boolean delete(long id) throws DAOException {
        int rowsAffected;
        try (db){
            db.connect();
            String query = "DELETE FROM threads WHERE id = ?";

            rowsAffected = db.executeUpdate(query);
            return (rowsAffected==1);

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Ottiene una lista di thread che corrispondono al titolo dato o parte di esso.
     *
     * @param title Titolo o parte di esso da cercare.
     * @param page  Numero della pagina.
     * @param limit Numero di Thread per pagina.
     * @param order Ordine per la lista (più votati, più recenti, più vecchi).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(String title, int page, int limit, Order order) throws DAOException {
        int offset = (page - 1) * limit;
        try (db){
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, "%" + title + "%", limit, offset);

            return mapper.map(rs);

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Ottiene una lista di thread corrispondenti al titolo e alla categoria.
     *
     * @param title    Titolo o parte di esso da cercare.
     * @param category Categoria del thread.
     * @param page  Numero della pagina.
     * @param limit Numero di Thread per pagina.
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByTitle(String title, Category category, int page, int limit, Order order) throws DAOException {
        int offset = (page - 1) * limit;
        try (db){
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ? AND category LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, "%" + title + "%", category.toString(), limit, offset);

            return mapper.map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Ottiene una lista di thread filtrati per categoria.
     *
     * @param category Categoria del thread.
     * @param page     Numero della pagina (paginazione).
     * @param limit Numero di Thread per pagina.
     * @param order    Ordine per la lista (più recenti, più vecchi, più votati).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    @Override
    public List<Thread> getThreadsByCategory(Category category ,int page, int limit,Order order) throws DAOException {
        int offset = (page - 1) * limit;
        try (db){
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE category LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, category.toString(), limit, offset);

            return mapper.map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
