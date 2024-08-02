package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableProperties;

import java.util.Objects;

public final class CreateTableQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final boolean ifNotExists;

    private CreateTableQuery(TableProperties<T> tableProperties, boolean ifNotExists) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
        this.ifNotExists = ifNotExists;
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public static final class Builder<T> implements QueryBuilder<T> {

        private TableProperties<T> tableProperties = null;
        private boolean ifNotExists = false;


        @Override
        public Builder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public Builder<T> withIfNotExists(boolean ifNotExists) {
            this.ifNotExists = ifNotExists;
            return this;
        }

        @Override
        public Query<T> build() {
            return new CreateTableQuery<>(tableProperties, ifNotExists);
        }
    }
}
