package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

public record CreateTableQuery<T>(TableData<T> tableData, boolean ifNotExists) implements Query<T> { }
