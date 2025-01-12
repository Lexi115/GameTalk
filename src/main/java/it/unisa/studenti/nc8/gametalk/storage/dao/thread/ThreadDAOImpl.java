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

public class ThreadDAOImpl extends DatabaseDAO<Thread> implements ThreadDAO {

    public ThreadDAOImpl(Database db, ResultSetMapper<Thread> mapper) {
        super(db, mapper);
    }

    @Override
    public Thread get(long id) throws DAOException {
        try {
            db.connect();
            Thread thread = null;

            String query = "SELECT * FROM threads WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);

            List<Thread> threads = mapper.map(rs);

            if(!threads.isEmpty()){
                thread = threads.getFirst();
            }

            db.close();
            return thread;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Thread> getAll() throws DAOException {
        try {
            db.connect();

            String query = "SELECT * FROM threads";

            ResultSet rs = db.executeQuery(query);

            List<Thread> threads = mapper.map(rs);

            db.close();
            return threads;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int save(Thread entity) throws DAOException {
        int rowsAffected;
        try {
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
            db.close();

            return rowsAffected;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int update(Thread entity) throws DAOException {
        int rowsAffected;
        try {
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
            db.close();
            return rowsAffected;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int delete(long id) throws DAOException {
        int rowsAffected;
        try {
            db.connect();
            String query = "DELETE FROM threads WHERE id = ?";

            rowsAffected = db.executeUpdate(query);
            db.close();
            return rowsAffected;

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Thread> getThreadsByTitle(String title, int page, Order order) throws DAOException {
        int limit = 12;
        int offset = (page - 1) * limit;
        try {
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, title, limit, offset);

            List<Thread> threads = mapper.map(rs);

            db.close();
            return threads;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Thread> getThreadsByTitle(String title, Category category, int page, Order order) throws DAOException {
        int limit = 12;
        int offset = (page - 1) * limit;
        try {
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE title LIKE ? AND category LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, title, category.toString(), limit, offset);

            List<Thread> threads = mapper.map(rs);

            db.close();
            return threads;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Thread> getThreadsByCategory(Category category ,int page ,Order order) throws DAOException {
        int limit = 12;
        int offset = (page - 1) * limit;
        try {
            db.connect();

            String baseQuery = "SELECT * FROM threads WHERE category LIKE ?";

            String query = switch (order) {
                case Oldest -> baseQuery + " ORDER BY creation_date ASC";
                case Newest -> baseQuery + " ORDER BY creation_date DESC";
                default -> baseQuery + " ORDER BY votes DESC";
            };

            query += " LIMIT ? OFFSET ?";

            ResultSet rs = db.executeQuery(query, category.toString(), limit, offset);

            List<Thread> threads = mapper.map(rs);

            db.close();
            return threads;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
