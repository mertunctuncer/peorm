package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;
import java.util.function.Predicate;

public final class SelectQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap selectData;

    private SelectQuery(TableData<T> tableData, IndexedSQLMap selectData) {
        this.tableData = tableData;
        this.selectData = selectData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getSelectData() {
        return selectData;
    }
    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap selectData;


        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }
        public

        public SelectQuery.Builder<T> setSelectData(T select, ReflectionData<T> reflectionData) {
            this.selectData = IndexedSQLMap.Factory.create(select, tableData, reflectionData);
            return this;
        }

        public SelectQuery.Builder<T> setSelectData(T select, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(select, tableData, reflectionData, allowFilter);
            return this;
        }

        public SelectQuery.Builder<T> setSelectData(IndexedSQLMap selectData) {
            this.selectData = selectData;
            return this;
        }

        public SelectQuery<T> build() {
            return new SelectQuery<>(
                    tableData,
                    Objects.requireNonNull(selectData, "Select data must be set")
            );
        }
    }
}
