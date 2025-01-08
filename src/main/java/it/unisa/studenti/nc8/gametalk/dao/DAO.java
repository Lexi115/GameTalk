package it.unisa.studenti.nc8.gametalk.dao;

import it.unisa.studenti.nc8.gametalk.dao.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {
    T findById(long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void save(T entity) throws DAOException;

    void update(T entity) throws DAOException;

    void delete(long id) throws DAOException;
}
