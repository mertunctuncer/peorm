package me.mertunctuncer.peorm.db;

import me.mertunctuncer.peorm.util.SQLMap;
import me.mertunctuncer.peorm.util.SQLPair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExecutableQuery extends ExecutableUpdate {

    private ResultSet resultSet = null;

    public ExecutableQuery(Connection connection) {
        super(connection);
    }

    @Override
    public void execute() throws SQLException {
        resultSet = getPreparedStatement().executeQuery();
    }

    public List<SQLMap> getResults() throws SQLException {
        List<SQLMap> results = new ArrayList<>();

        int columnCount = resultSet.getMetaData().getColumnCount();
        while(resultSet.next()) {
            List<SQLPair> dataPairs = new ArrayList<>();
            for(int i = 1; i <= columnCount; i++) {
                dataPairs.add(new SQLPair(resultSet.getMetaData().getColumnName(i), resultSet.getString(i)));
            }
            results.add(new SQLMap(dataPairs));
        }

        return results;
    }

    @Override
    public void close() throws SQLException{
        if(resultSet != null) resultSet.close();
        super.close();
    }
}
