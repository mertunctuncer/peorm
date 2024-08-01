package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.dao.DAO;
import me.mertunctuncer.peorm.dao.DAOBuilder;
import me.mertunctuncer.peorm.model.FetchingQueryResult;
import me.mertunctuncer.peorm.query.*;
import me.mertunctuncer.peorm.model.QueryResult;
import me.mertunctuncer.peorm.syntax.SyntaxProvider;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class VirtualDatabaseController implements DatabaseController{




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

    public <T> FetchingQueryResult<IndexedSQLMap> fetch(Query<T> query) {
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


}
