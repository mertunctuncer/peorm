package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

public final class UpsertQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap queryData;

    private UpsertQuery(TableData<T> tableData, IndexedSQLMap queryData) {
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

        public final TableData<T> tableData;
        public IndexedSQLMap values;

        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public UpsertQuery.Builder<T> setValues(T values, ReflectionData<T> reflectionData) {
            this.values = IndexedSQLMap.Factory.create(values, tableData, reflectionData);
            return this;
        }

        public UpsertQuery.Builder<T> setValues(IndexedSQLMap values) {
            this.values = values;
            return this;
        }

        public UpsertQuery<T> build() {
            return new UpsertQuery<>(tableData, values);
        }
    }
}
