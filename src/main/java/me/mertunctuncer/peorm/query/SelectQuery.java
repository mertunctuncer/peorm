package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;
import java.util.function.Predicate;

public final class SelectQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap whereData;
    private final boolean isFetchAll;

    private SelectQuery(TableData<T> tableData, IndexedSQLMap whereData, boolean isFetchAll) {
        this.tableData = tableData;
        this.isFetchAll = isFetchAll;
        if(!isFetchAll) this.whereData = Objects.requireNonNull(whereData, "Where must not be null if the query is not fetch all");
        else this.whereData = whereData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getWhereData() {
        return whereData;
    }

    public boolean isFetchAll() {
        return isFetchAll;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap selectData;
        private boolean isFetchAll = false;


        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }
        public SelectQuery.Builder<T> fetchAll(boolean isFetchAll) {
            this.isFetchAll = isFetchAll;
            return this;
        }

        public SelectQuery.Builder<T> where(T where, ReflectionData<T> reflectionData) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableData, reflectionData);
            return this;
        }

        public SelectQuery.Builder<T> where(T where, ReflectionData<T> reflectionData, Predicate<ColumnData> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableData, reflectionData, allowFilter);
            return this;
        }

        public SelectQuery.Builder<T> where(IndexedSQLMap where) {
            this.selectData = where;
            return this;
        }

        public SelectQuery<T> build() {
            return new SelectQuery<>(
                    tableData,
                    Objects.requireNonNull(selectData, "Select data must be set"),
                    isFetchAll
            );
        }
    }
}
