package it.unisa.studenti.nc8.gametalk.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    boolean connect() throws SQLException;
    boolean isConnected() throws SQLException;
    boolean close() throws SQLException;

    ResultSet executeQuery(String query, Object... parameters) throws SQLException;
    int executeUpdate(String query, Object... parameters) throws SQLException;
    int executeUpdateReturnKeys(String query, Object... parameters) throws SQLException;
}
