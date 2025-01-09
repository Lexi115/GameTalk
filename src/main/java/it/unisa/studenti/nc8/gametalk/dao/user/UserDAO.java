package it.unisa.studenti.nc8.gametalk.dao.user;

import it.unisa.studenti.nc8.gametalk.dao.DAO;
import it.unisa.studenti.nc8.gametalk.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.dao.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.user.User;

import java.util.List;

public class UserDAO extends DatabaseDAO<User> implements DAO<User> {

    public UserDAO(Database db, ResultSetMapper<User> mapper) {
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
