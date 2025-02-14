package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryResult extends AutoCloseable {
    ResultSet getResultSet() throws SQLException;
}
