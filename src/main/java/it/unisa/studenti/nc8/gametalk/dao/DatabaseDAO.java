package it.unisa.studenti.nc8.gametalk.dao;

import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.ResultSetMapper;

public abstract class DatabaseDAO<T> {
    protected Database db;
    protected ResultSetMapper<T> mapper;

    public DatabaseDAO(Database db, ResultSetMapper<T> mapper) {
        this.db = db;
        this.mapper = mapper;
    }
}
