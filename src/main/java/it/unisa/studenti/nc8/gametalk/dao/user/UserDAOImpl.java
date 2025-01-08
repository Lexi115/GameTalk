package it.unisa.studenti.nc8.gametalk.dao.user;

import it.unisa.studenti.nc8.gametalk.dao.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> fetch(ResultSet rs) throws SQLException {
        return List.of();
    }

    @Override
    public User findById(long id) throws DAOException {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(long id) {

    }
}
