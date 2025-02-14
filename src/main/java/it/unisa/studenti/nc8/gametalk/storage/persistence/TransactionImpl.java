package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionImpl implements Transaction {
    private final Connection connection;
    private boolean committed = false;

    public TransactionImpl(
            final Connection connection
    ) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if (!committed) {
            connection.rollback();
        }
        connection.setAutoCommit(true);
    }
}
