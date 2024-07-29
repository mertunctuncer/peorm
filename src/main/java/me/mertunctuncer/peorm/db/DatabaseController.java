package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.model.FetchingQueryResult;
import me.mertunctuncer.peorm.query.*;
import me.mertunctuncer.peorm.model.QueryResult;
import me.mertunctuncer.peorm.syntax.SyntaxProvider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public final class DatabaseController implements AutoCloseable{

    private final ConnectionSource connectionSource;
    private final SyntaxProvider syntaxProvider;
    private final ExecutorService executorService;

    public DatabaseController(
            ConnectionSource connectionSource,
            SyntaxProvider syntaxProvider,
            ExecutorService executorService
    ) {
        this.connectionSource = connectionSource;
        this.syntaxProvider = syntaxProvider;
        this.executorService = executorService;
    }

    public <T> QueryResult execute(Query<T> query) {
        try (
                StatementExecutor statementExecutor = new StatementExecutor(
                        connectionSource.getConnection(),
                        syntaxProvider.getSyntax(query),
                        query.getParameters()
                )
        ) {
            statementExecutor.execute();
            return new QueryResult(true);
        } catch (SQLException e) {
            return new QueryResult(e);
        }
    }

    public <T> FetchingQueryResult<Map<String, Object>> fetch(Query<T> query) {
        try (
                StatementExecutor statementExecutor = new StatementExecutor(
                        connectionSource.getConnection(),
                        syntaxProvider.getSyntax(query),
                        query.getParameters()
                )
        ) {
            List<Map<String, Object>> result = statementExecutor.fetch();

            return new FetchingQueryResult<>(result);
        } catch (SQLException e) {
            return new FetchingQueryResult<>(e);
        }
    }

    public boolean fetchTableExists(String tableName) {
        Connection connection = null;
        DatabaseMetaData meta;
        ResultSet results = null;

        try {
            connection = connectionSource.getConnection();
            meta = connection.getMetaData();
            results = meta.getTables(null, null, tableName, null);
            return results.next();
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (results != null) results.close();
                if (connection != null) connection.close();
            } catch (SQLException ignored) {}
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
        connectionSource.close();
    }
}