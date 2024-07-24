package me.mertunctuncer.peorm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExecutableStatement implements AutoCloseable{

    private final String statement;
    private final Connection connection;
    private final List<Object> parameters;
    private final boolean hasResult;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ExecutableStatement(Connection connection, String statement, List<Object> parameters, boolean hasResult) {
        this.statement = Objects.requireNonNull(statement, "Statement must not be null");
        this.connection = Objects.requireNonNull(connection, "Connection must not be null");
        this.parameters = parameters;
        this.hasResult = hasResult;
    }

    public RawResult execute() {
        try {
            preparedStatement = connection.prepareStatement(statement);
            if(parameters != null) {
                int index = 1;
                for(Object param : parameters) preparedStatement.setObject(index++, param);
            }

            if(hasResult) {
                resultSet = preparedStatement.executeQuery();
                return RawResult.success(asList(resultSet, parameters != null ? parameters.size() : 0));
            } else {
                preparedStatement.executeUpdate();
                return RawResult.success();
            }
        } catch (SQLException e) {
            return RawResult.fail(e);
            // TODO LOGGING MAYBE?
        }
    }

    private static List<List<Object>> asList(ResultSet resultSet, int count) throws SQLException {
        List<List<Object>> results = new ArrayList<>();
        while(resultSet.next()) {
            results.add(new ArrayList<>());
            for(int i = 1; i <= count; i++) {
                results.getLast().add(resultSet.getObject(i));
            }
        }
        return results;
    }

    @Override
    public void close() throws SQLException {
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        connection.close();
    }
}
