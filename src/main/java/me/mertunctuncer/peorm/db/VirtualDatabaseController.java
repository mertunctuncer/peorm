package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.db.query.Query;
import me.mertunctuncer.peorm.model.FetchingQueryResult;
import me.mertunctuncer.peorm.model.QueryResult;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public <T> FetchingQueryResult<SQLPairList> fetch(Query<T> query) {
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
