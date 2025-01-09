package it.unisa.studenti.nc8.gametalk.dao.thread;

import it.unisa.studenti.nc8.gametalk.dao.DAO;
import it.unisa.studenti.nc8.gametalk.dao.DatabaseDAO;
import it.unisa.studenti.nc8.gametalk.dao.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.post.thread.Thread;

import java.util.List;

public class ThreadDAO extends DatabaseDAO<Thread> implements DAO<Thread> {

    public ThreadDAO(Database db, ResultSetMapper<Thread> mapper) {
        super(db, mapper);
    }

    @Override
    public Thread get(long id) throws DAOException {
        return null;
    }

    @Override
    public List<Thread> getAll() throws DAOException {
        return List.of();
    }

    @Override
    public boolean save(Thread entity) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Thread entity) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        return false;
    }
}
