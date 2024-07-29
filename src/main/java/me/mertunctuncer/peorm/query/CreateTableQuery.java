package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.util.List;

public record CreateTableQuery<T>(TableData<T> tableData, boolean ifNotExists) implements Query<T> {

    @Override
    public List<Object> getParameters() {
        // TODO
    }

}
