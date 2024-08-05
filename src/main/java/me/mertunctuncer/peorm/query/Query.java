package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableProperties;

public sealed interface Query<T>
        permits CreateTableQuery,
        DropTableQuery,
        InsertQuery,
        SelectQuery,
        UpdateQuery,
        DeleteQuery,
        UpsertQuery
{
    TableProperties<T> getTableProperties();
}
