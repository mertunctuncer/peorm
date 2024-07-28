package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public final class DropTableQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    public DropTableQuery(final TableData<T> tableData) {
        this.tableData = tableData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
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
