package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.db.dao.DataAccessObject;
import me.mertunctuncer.peorm.db.dao.DAOBuilder;
import me.mertunctuncer.peorm.model.QueryResult;
import me.mertunctuncer.peorm.db.syntax.SyntaxProvider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public abstract class DatabaseController implements AutoCloseable {

    private final ConnectionSource connectionSource;
    private final SyntaxProvider syntaxProvider;
    private ExecutorService executorService = null;
    private final Map<Class<?>, DataAccessObject<?>> tableAccessProviders = new ConcurrentHashMap<>();

    public DatabaseController(
            ConnectionSource connectionSource,
            SyntaxProvider syntaxProvider,
            ExecutorService executorService
    ) {
        this.connectionSource = connectionSource;
        this.syntaxProvider = syntaxProvider;
        this.executorService = executorService;
    }

    public <T> DataAccessObject<T> createDAO(Class<T> clazz, T defaults) {
        DAOBuilder<T> daoBuilder = new DAOBuilder<>(clazz, this);
        if(defaults != null) daoBuilder.setDefaults(defaults);
        return daoBuilder.build();
    }

    public <T> DataAccessObject<T> getDAO(Class<T> clazz) {
        return (DataAccessObject<T>) tableAccessProviders.get(clazz);
    }

    public <T> void register(Class<T> clazz, DataAccessObject<T> dataAccessObject) {
        tableAccessProviders.put(clazz, dataAccessObject);
    }


    public abstract ExecutorService getExecutor();

    public QueryResult fetchTableExists(String tableName) {
        Connection connection = null;
        DatabaseMetaData meta;
        ResultSet results = null;

        try {
            connection = connectionSource.getConnection();
            meta = connection.getMetaData();
            results = meta.getTables(null, null, tableName, null);
            return new QueryResult(results.next());
        } catch (SQLException e) {
            return new QueryResult(e);
        } finally {
            try {
                if (results != null) results.close();
                if (connection != null) connection.close();
            } catch (SQLException ignored) {}
        }
    }


    @Override
    public void close() throws Exception {
        executorService.close();
        connectionSource.close();
    }




}
