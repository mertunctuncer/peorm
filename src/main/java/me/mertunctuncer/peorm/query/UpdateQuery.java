package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;


import java.util.Objects;
import java.util.function.Predicate;

public final class UpdateQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap selectData;
    private final IndexedSQLMap updateData;

    private UpdateQuery(TableData<T> tableData, IndexedSQLMap selectData, IndexedSQLMap updateData) {
        this.tableData = tableData;
        this.selectData = selectData;
        this.updateData = updateData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getSelectData() {
        return selectData;
    }

    public IndexedSQLMap getUpdateData() {
        return updateData;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap selectData;
        private IndexedSQLMap updateData;


        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public UpdateQuery.Builder<T> setSelectData(T select, ReflectionData<T> reflectionData) {
            this.selectData = IndexedSQLMap.Factory.create(select, tableData, reflectionData);
            return this;
        }

        public UpdateQuery.Builder<T> setSelectData(T select, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(select, tableData, reflectionData, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> setSelectData(IndexedSQLMap selectData) {
            this.selectData = selectData;
            return this;
        }

        public UpdateQuery.Builder<T> setUpdateData(T update, ReflectionData<T> reflectionData) {
            this.updateData = IndexedSQLMap.Factory.create(update, tableData, reflectionData);
            return this;
        }

        public UpdateQuery.Builder<T> setUpdateData(T update, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.updateData = IndexedSQLMap.Factory.create(update, tableData, reflectionData, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> setUpdateData(IndexedSQLMap updateData) {
            this.updateData = updateData;
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
