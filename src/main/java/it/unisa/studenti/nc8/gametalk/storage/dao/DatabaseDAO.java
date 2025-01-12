package it.unisa.studenti.nc8.gametalk.storage.dao;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;

public abstract class DatabaseDAO<T> {
    protected final Database db;
    protected final ResultSetMapper<T> mapper;

    public DatabaseDAO(Database db, ResultSetMapper<T> mapper) {
        this.db = db;
        this.mapper = mapper;
    }
}
