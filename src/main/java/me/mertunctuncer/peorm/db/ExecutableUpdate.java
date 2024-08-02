package me.mertunctuncer.peorm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ExecutableUpdate implements AutoCloseable{

    private final Connection connection;
    private PreparedStatement preparedStatement = null;

    public ExecutableUpdate(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "Connection must not be null");
    }

    public void prepare(String statement) throws SQLException {
        this.preparedStatement = connection.prepareStatement(statement);
    }

    public void setParameters(List<Object> parameters) throws SQLException {
        int index = 1;
        for (Object param : parameters) preparedStatement.setObject(index++, param);
    }

    public void execute() throws SQLException {
        this.preparedStatement.executeUpdate();
    }

    protected PreparedStatement getPreparedStatement() {
        return this.preparedStatement;
    }

    @Override
    public void close() throws SQLException {
        if(preparedStatement != null) preparedStatement.close();
        connection.close();
    }
}
