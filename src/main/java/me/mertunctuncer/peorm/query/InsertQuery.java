package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;


public final class InsertQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap rowData;

    private InsertQuery(TableData<T> tableData, IndexedSQLMap rowData) {
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

        private final TableData<T> tableData;
        private IndexedSQLMap rowData;

        public Builder(TableData<T> tableData) {
            this.tableData = Objects.requireNonNull(tableData, "Table data must not be null");
        }

        public Builder<T> rowData(T values, ReflectionData<T> reflectionData) {
            this.rowData = IndexedSQLMap.Factory.create(values, tableData, reflectionData, columnData -> columnData.autoIncrement() != null);
            return this;
        }

        public Builder<T> rowData(IndexedSQLMap values) {
            this.rowData = values;
            return this;
        }

        public InsertQuery<T> build() {
            return new InsertQuery<>(tableData, Objects.requireNonNull(rowData, "Values must be set"));
        }
    }
}
