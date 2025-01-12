package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMock implements Database {

    @Override
    public void connect() {
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public ResultSet executeQuery(String query, Object... parameters) {
        return null;
    }

    @Override
    public int executeUpdate(String query, Object... parameters) {
        return 0;
    }

    @Override
    public int executeUpdateReturnKeys(String query, Object... parameters) {
        return 0;
    }
}
