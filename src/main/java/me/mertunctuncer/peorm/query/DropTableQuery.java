package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;


public record DropTableQuery<T>(TableData<T> tableData) implements Query<T> { }
