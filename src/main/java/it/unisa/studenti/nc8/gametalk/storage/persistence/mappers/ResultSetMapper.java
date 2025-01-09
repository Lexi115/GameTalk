package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetMapper<T> {
    List<T> map(ResultSet rs) throws SQLException;
}
