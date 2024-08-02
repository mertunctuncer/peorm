package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;


public final class InsertQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final IndexedSQLMap rowData;

    private InsertQuery(TableProperties<T> tableProperties, IndexedSQLMap rowData) {
        this.tableProperties = tableProperties;
        this.rowData = rowData;
    }

    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public IndexedSQLMap getRowData() {
        return rowData;
    }

    public static final class Builder<T> {

        private final TableProperties<T> tableProperties;
        private IndexedSQLMap rowData;

        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = Objects.requireNonNull(tableProperties, "Table data must not be null");
        }

        public Builder<T> rowData(T values, ReflectionContainer<T> reflectionContainer) {
            this.rowData = IndexedSQLMap.Factory.create(values, tableProperties, reflectionContainer, columnData -> columnData.autoIncrement() != null);
            return this;
        }

        public Builder<T> rowData(IndexedSQLMap values) {
            this.rowData = values;
            return this;
        }

        public InsertQuery<T> build() {
            return new InsertQuery<>(tableProperties, Objects.requireNonNull(rowData, "Values must be set"));
        }
    }
}
