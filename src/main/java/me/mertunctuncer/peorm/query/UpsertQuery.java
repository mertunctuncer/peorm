package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.TableData;

public record UpsertQuery<T>(T data, TableData<T> tableData) implements Query<T> { }
