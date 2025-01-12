package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl extends DatabaseDAO<User> implements UserDAO {

    public UserDAOImpl(Database db, ResultSetMapper<User> mapper) {
        super(db, mapper);
    }

    @Override
    public User get(long id) throws DAOException {
        try (db) {
            db.connect();
            String query = "SELECT * FROM users WHERE id = ?";

            ResultSet rs = db.executeQuery(query, id);
            List<User> users = mapper.map(rs);

            return !users.isEmpty() ? users.getFirst() : null;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        try (db) {
            db.connect();
            String query = "SELECT * FROM users";

            ResultSet rs = db.executeQuery(query);
            return mapper.map(rs);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int save(User entity) throws DAOException {
        try (db) {
            db.connect();
            String query = "INSERT INTO users (id, username, password_hash, creation_date, " +
                    "banned, strikes, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

            Object[] params = {
                    entity.getId(),
                    entity.getUsername(),
                    entity.getPasswordHash(),
                    entity.getCreationDate(),
                    entity.isBanned(),
                    entity.getStrikes(),
                    entity.getRole().toString()
            };

            return db.executeUpdate(query, params);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int update(User entity) throws DAOException {
        try (db) {
            db.connect();
            String query = "UPDATE users SET id = ?, username = ?, password_hash = ?, " +
                    "creation_date = ?, banned = ?, strikes = ?, roles = ? WHERE id = ?";

            Object[] params = {
                    entity.getId(),
                    entity.getUsername(),
                    entity.getPasswordHash(),
                    entity.getCreationDate(),
                    entity.isBanned(),
                    entity.getStrikes(),
                    entity.getRole().toString(),
                    entity.getId()
            };

            return db.executeUpdate(query, params);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public int delete(long id) throws DAOException {
        try (db) {
            db.connect();
            String query = "DELETE FROM users WHERE id = ?";

            return db.executeUpdate(query, id);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsersByUsername(String username, int page) {
        return List.of();
    }

    @Override
    public List<User> getStruckUsers(int page) {
        return List.of();
    }

    @Override
    public List<User> getBannedUsers(int page) {
        return List.of();
    }
}
