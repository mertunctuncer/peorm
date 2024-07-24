package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.model.Result;
import me.mertunctuncer.peorm.query.Query;
import me.mertunctuncer.peorm.syntax.SyntaxProvider;

import java.sql.SQLException;

public class DatabaseExecutor {

    private final SyntaxProvider syntaxProvider;
    private final ConnectionSource connectionSource;

    protected DatabaseExecutor(SyntaxProvider syntaxProvider, ConnectionSource connectionSource) {
        this.syntaxProvider = syntaxProvider;
        this.connectionSource = connectionSource;
    }

    public Result execute(Query query) {
        try (
            ExecutableStatement executableStatement = new ExecutableStatement(
                connectionSource.getConnection(),
                syntaxProvider.getSyntax(query),
                query.getParameters(),
                query.isFetching()
            )
        ) {
            return executableStatement.execute();
        } catch (SQLException e) {
            return RawResult.fail(e);
        }
    }
}
