package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.util.List;

public class UserDAOImpl extends DatabaseDAO<User> implements UserDAO {

    public UserDAOImpl(Database db, ResultSetMapper<User> mapper) {
        super(db, mapper);
    }

    @Override
    public User get(long id) throws DAOException {
        return null;
    }

    @Override
    public List<User> getAll() throws DAOException {
        return List.of();
    }

    @Override
    public boolean save(User entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(User entity) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        return false;
    }
}
