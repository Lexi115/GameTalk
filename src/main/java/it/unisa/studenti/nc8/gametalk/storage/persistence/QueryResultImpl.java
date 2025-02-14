package it.unisa.studenti.nc8.gametalk.storage.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryResultImpl implements QueryResult {
    private final ResultSet resultSet;
    private final Statement statement;

    public QueryResultImpl(
            final ResultSet resultSet,
            final Statement statement
    ) {
        this.resultSet = resultSet;
        this.statement = statement;
    }

    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}