package me.mertunctuncer.peorm.db.query;

import me.mertunctuncer.peorm.model.TableProperties;

public interface QueryBuilder<T> {
    QueryBuilder<T> withTableProperties(TableProperties<T> tableProperties);
    Query<T> build();
}
