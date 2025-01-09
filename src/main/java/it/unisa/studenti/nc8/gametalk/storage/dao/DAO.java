package it.unisa.studenti.nc8.gametalk.storage.dao;

import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {
    T get(long id) throws DAOException;

    List<T> getAll() throws DAOException;

    boolean save(T entity) throws DAOException;

    boolean update(T entity) throws DAOException;

    boolean delete(long id) throws DAOException;
}
