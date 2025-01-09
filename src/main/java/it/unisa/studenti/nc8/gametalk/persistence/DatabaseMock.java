package it.unisa.studenti.nc8.gametalk.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMock implements Database{

    @Override
    public boolean connect() throws SQLException {
        return false;
    }

    @Override
    public boolean isConnected() throws SQLException {
        return false;
    }

    @Override
    public boolean close() throws SQLException {
        return false;
    }

    @Override
    public ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String query, Object... parameters) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdateReturnKeys(String query, Object... parameters) throws SQLException {
        return 0;
    }
}
