package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

public final class UpsertQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap rowData;

    private UpsertQuery(TableData<T> tableData, IndexedSQLMap rowData) {
        this.tableData = tableData;
        this.rowData = rowData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getRowData() {
        return rowData;
    }

    public static final class Builder<T> {

        public final TableData<T> tableData;
        public IndexedSQLMap values;

        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public UpsertQuery.Builder<T> rowData(T rowData, ReflectionData<T> reflectionData) {
            this.values = IndexedSQLMap.Factory.create(rowData, tableData, reflectionData);
            return this;
        }

        public UpsertQuery.Builder<T> rowData(IndexedSQLMap rowData) {
            this.values = rowData;
            return this;
        }

        public UpsertQuery<T> build() {
            return new UpsertQuery<>(tableData, values);
        }
    }
}
