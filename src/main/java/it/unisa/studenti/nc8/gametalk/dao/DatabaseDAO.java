package it.unisa.studenti.nc8.gametalk.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseDAO<T> extends DAO<T> {
    List<T> fetch(ResultSet rs) throws SQLException;
}
