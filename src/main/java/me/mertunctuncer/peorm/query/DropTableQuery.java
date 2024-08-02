package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableProperties;

import java.util.Objects;


public final class DropTableQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;

    private DropTableQuery(TableProperties<T> tableProperties) {
        this.tableProperties = tableProperties;
    }

    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public static final class Builder<T> {

        private final TableProperties<T> tableProperties;

        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = Objects.requireNonNull(tableProperties, "Table data must not be null");
        }

        public DropTableQuery<T> build() {
            return new DropTableQuery<>(tableProperties);
        }
    }
}
