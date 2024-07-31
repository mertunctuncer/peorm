package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatementExecutor implements AutoCloseable{

    private final String statement;
    private final Connection connection;
    private final List<Object> parameters;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public StatementExecutor(Connection connection, String statement, List<Object> parameters) {
        this.statement = Objects.requireNonNull(statement, "Statement must not be null");
        this.connection = Objects.requireNonNull(connection, "Connection must not be null");
        this.parameters = parameters;
    }

    public void execute() throws SQLException {
        prepareAndSet();
        preparedStatement.executeUpdate();
    }

    public List<IndexedSQLMap> fetch() throws SQLException {
        prepareAndSet();
        resultSet = preparedStatement.executeQuery();

        List<IndexedSQLMap> results = new ArrayList<>();

        int columnCount = preparedStatement.getMetaData().getColumnCount();
        while(resultSet.next()) {
            results.add(new IndexedSQLMap());
            for(int i = 1; i <= columnCount; i++) {
                results.getLast().put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
        }
        return results;
    }

    private void prepareAndSet() throws SQLException {
        preparedStatement = connection.prepareStatement(statement);
        if (parameters != null) {
            int index = 1;
            for (Object param : parameters) preparedStatement.setObject(index++, param);
        }
    }

    @Override
    public void close() throws SQLException {
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        connection.close();
    }
}
