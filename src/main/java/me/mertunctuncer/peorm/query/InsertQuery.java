package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;


public final class InsertQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap queryData;

    private InsertQuery(TableData<T> tableData, IndexedSQLMap queryData) {
        this.tableData = tableData;
        this.queryData = queryData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getQueryData() {
        return queryData;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap values;

        public Builder(TableData<T> tableData) {
            this.tableData = Objects.requireNonNull(tableData, "Table data must not be null");
        }

        public Builder<T> setValues(T values, ReflectionData<T> reflectionData) {
            this.values = IndexedSQLMap.Factory.create(values, tableData, reflectionData, columnData -> columnData.autoIncrement() != null);
            return this;
        }

        public Builder<T> setValues(IndexedSQLMap values) {
            this.values = values;
            return this;
        }

        public InsertQuery<T> build() {
            return new InsertQuery<>(tableData, Objects.requireNonNull(values, "Values must be set"));
        }
    }
}
