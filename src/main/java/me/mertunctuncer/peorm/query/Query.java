package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

public sealed interface Query<T>
        permits CreateTableQuery,
        DropTableQuery,
        InsertQuery,
        SelectQuery,
        UpdateQuery,
        DeleteQuery,
        UpsertQuery
{
    TableData<T> getTableData();
}
