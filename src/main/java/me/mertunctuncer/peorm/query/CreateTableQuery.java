package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public final class CreateTableQuery<T> implements Query<T> {
    
    private final TableData<T> tableData;
    private final boolean ifNotExists;

    public CreateTableQuery(TableData<T> tableData, boolean ifNotExists) {
        this.tableData = tableData;
        this.ifNotExists = ifNotExists;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    @Override
    public List<Object> getParameters() {
        return null;
    }

    @Override
    public boolean isFetching() {
        return false;
    }

}
