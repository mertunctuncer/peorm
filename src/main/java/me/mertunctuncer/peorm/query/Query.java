package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public sealed interface Query<T>
        permits CreateTableQuery,
        DropTableQuery,
        InsertQuery,
        SelectQuery,
        UpdateQuery,
        DeleteQuery,
        UpsertQuery
{
    TableData<T> tableData();
    List<Object> getParameters();
}
