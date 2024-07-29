package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public record InsertQuery<T>(T data, TableData<T> tableData) implements Query<T> {

    @Override
    public List<Object> getParameters() {
        // TODO IMPLEMENT
    }
}
