package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

public final class UpsertQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final IndexedSQLMap rowData;

    private UpsertQuery(TableProperties<T> tableProperties, IndexedSQLMap rowData) {
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

        public final TableProperties<T> tableProperties;
        public IndexedSQLMap values;

        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
        }

        public UpsertQuery.Builder<T> rowData(T rowData, ReflectionContainer<T> reflectionContainer) {
            this.values = IndexedSQLMap.Factory.create(rowData, tableProperties, reflectionContainer);
            return this;
        }

        public UpsertQuery.Builder<T> rowData(IndexedSQLMap rowData) {
            this.values = rowData;
            return this;
        }

        public UpsertQuery<T> build() {
            return new UpsertQuery<>(tableProperties, values);
        }
    }
}
