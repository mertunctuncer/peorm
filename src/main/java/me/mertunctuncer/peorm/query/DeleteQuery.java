package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;

public final class DeleteQuery<T> implements Query<T> {

    private final TableData<T> tableData;
    private final IndexedSQLMap whereData;
    private final boolean isDeleteAll;

    private DeleteQuery(TableData<T> tableData, IndexedSQLMap whereData, boolean isDeleteAll) {
        this.tableData = tableData;
        this.isDeleteAll = isDeleteAll;
        if(!isDeleteAll) this.whereData = Objects.requireNonNull(whereData, "Where must not be null if the query is not delete all");
        else this.whereData = whereData;
    }
    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public IndexedSQLMap getWhereData() {
        return whereData;
    }

    public boolean isDeleteAll() {
        return isDeleteAll;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private IndexedSQLMap where = null;
        private boolean isDeleteAll = false;

        public Builder(TableData<T> tableData) {
            this.tableData = Objects.requireNonNull(tableData, "Table data must not be null");
        }

        public DeleteQuery.Builder<T> deleteAll(boolean isDeleteAll) {
            this.isDeleteAll = isDeleteAll;
            return this;
        }

        public DeleteQuery.Builder<T> where(T where, ReflectionData<T> reflectionData) {
            this.where = IndexedSQLMap.Factory.create(where, tableData, reflectionData, ColumnData::primaryKey);
            return this;
        }

        public DeleteQuery.Builder<T> where(IndexedSQLMap where) {
            this.where = where;
            return this;
        }

        public DeleteQuery<T> build() {
            return new DeleteQuery<>(tableData, Objects.requireNonNull(where, "Values must be set"), isDeleteAll);
        }
    }
}
