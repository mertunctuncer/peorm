package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public final class UpsertQuery<T> implements Query<T> {

    private final T data;
    private final TableData<T> tableData;

    public UpsertQuery(T data, TableData<T> tableData) {
        this.data = data;
        this.tableData = tableData;
    }

    @Override
    public TableData<T> tableData() {
        return tableData;
    }

    @Override
    public List<Object> getParameters() {
        return List.of();
    }
}
