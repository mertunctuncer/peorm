package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;


import java.util.Objects;
import java.util.function.Predicate;

public final class UpdateQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap where;
    private final IndexedSQLMap updateData;

    private UpdateQuery(TableData<T> tableData, IndexedSQLMap where, IndexedSQLMap rowData) {
        this.tableData = tableData;
        this.where = where;
        this.updateData = rowData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getWhereData() {
        return where;
    }

    public IndexedSQLMap getRowData() {
        return updateData;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap selectData;
        private IndexedSQLMap updateData;


        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public UpdateQuery.Builder<T> where(T where, ReflectionData<T> reflectionData) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableData, reflectionData);
            return this;
        }

        public UpdateQuery.Builder<T> where(T where, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableData, reflectionData, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> where(IndexedSQLMap where) {
            this.selectData = where;
            return this;
        }

        public UpdateQuery.Builder<T> rowData(T rowData, ReflectionData<T> reflectionData) {
            this.updateData = IndexedSQLMap.Factory.create(rowData, tableData, reflectionData);
            return this;
        }

        public UpdateQuery.Builder<T> rowData(T rowData, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.updateData = IndexedSQLMap.Factory.create(rowData, tableData, reflectionData, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> rowData(IndexedSQLMap rowData) {
            this.updateData = rowData;
            return this;
        }

        public UpdateQuery<T> build() {
            return new UpdateQuery<>(
                    tableData,
                    Objects.requireNonNull(selectData, "Select data must be set"),
                    Objects.requireNonNull(updateData, "Update data must be set")
            );
        }
    }
}
