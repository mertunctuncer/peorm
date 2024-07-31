package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.lang.reflect.Field;

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

        public final TableData<T> tableData;
        public IndexedSQLMap values;

        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public Builder<T> setValues(T values, ReflectionData<T> reflectionData) {
            IndexedSQLMap indexedSQLMap = new IndexedSQLMap();

            for(ColumnData columnData : tableData.columns()) {
                if(columnData.autoIncrement() != null) continue;
                Field field = reflectionData.getColumnFieldMap().get(columnData.name());
                try {
                    indexedSQLMap.put(columnData.name(), field.get(values));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            this.values = indexedSQLMap;
            return this;
        }

        public Builder<T> setValues(IndexedSQLMap values) {
            this.values = values;
            return this;
        }

        public InsertQuery<T> build() {
            return new InsertQuery<>(tableData, values);
        }
    }
}
