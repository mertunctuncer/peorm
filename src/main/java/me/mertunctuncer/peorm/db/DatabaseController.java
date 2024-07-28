package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.query.*;
import me.mertunctuncer.peorm.model.QueryResult;
import me.mertunctuncer.peorm.syntax.SyntaxProvider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DatabaseController implements AutoCloseable{

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

    public <T> QueryResult<T> execute(Query<T> query) {

        String syntax = switch (query){
            case CreateTableQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case DeleteQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case DropTableQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case InsertQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case SelectQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case UpdateQuery<T> q -> getSyntaxProvider().getSyntax(q);
            case UpsertQuery<T> q -> getSyntaxProvider().getSyntax(q);
        };

        try (
                StatementExecutor statementExecutor = new StatementExecutor(
                        connectionSource.getConnection(),
                        syntax,
                        query.getParameters(),
                        query.isFetching()
                )
        ) {
            QueryResult<List<Object>> rawResults = statementExecutor.execute();

        } catch (SQLException e) {
            return new QueryResult<>(false);
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

    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public SyntaxProvider getSyntaxProvider() {
        return syntaxProvider;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
        connectionSource.close();
    }
}
