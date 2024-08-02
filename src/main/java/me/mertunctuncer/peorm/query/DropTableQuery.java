package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableProperties;

import java.util.Objects;


public final class DropTableQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;

    private DropTableQuery(TableProperties<T> tableProperties) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public static final class Builder<T> implements QueryBuilder<T>{

        private TableProperties<T> tableProperties;

        @Override
        public QueryBuilder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public DropTableQuery<T> build() {
            return new DropTableQuery<>(tableProperties);
        }
    }
}
