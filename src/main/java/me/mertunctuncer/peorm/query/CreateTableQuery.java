package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.Builder;

import java.util.Objects;

public final class CreateTableQuery<T> implements Query<T> {
    private final TableProperties<T> tableProperties;
    private final boolean ifNotExists;

    private CreateTableQuery(TableProperties<T> tableProperties, boolean ifNotExists) {
        this.tableProperties = tableProperties;
        this.ifNotExists = ifNotExists;
    }

    public static Builder<T> builder(TableProperties<T> tableProperties) {
        return new Builder<T>(tableProperties);
    }

    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public static final class Builder<T> implements me.mertunctuncer.peorm.util.Builder<Query<T>> {

        private final TableProperties<T> tableProperties;
        private boolean ifNotExists = false;

        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = Objects.requireNonNull(tableProperties, "Table data must not be null");
        }

        public Builder<T> setIfNotExists(boolean ifNotExists) {
            this.ifNotExists = ifNotExists;
            return this;
        }

        @Override
        public Query<T> build() {
            return new CreateTableQuery<>(tableProperties, ifNotExists);
        }
    }
}
