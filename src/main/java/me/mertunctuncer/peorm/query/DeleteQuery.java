package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;

public final class DeleteQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final IndexedSQLMap whereData;
    private final boolean isDeleteAll;

    private DeleteQuery(TableProperties<T> tableProperties, IndexedSQLMap whereData, boolean isDeleteAll) {
        this.tableProperties = tableProperties;
        this.isDeleteAll = isDeleteAll;
        if(!isDeleteAll) this.whereData = Objects.requireNonNull(whereData, "Where must not be null if the query is not delete all");
        else this.whereData = whereData;
    }
    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public IndexedSQLMap getWhereData() {
        return whereData;
    }

    public boolean isDeleteAll() {
        return isDeleteAll;
    }

    public static final class Builder<T> {

        private final TableProperties<T> tableProperties;
        private IndexedSQLMap where = null;
        private boolean isDeleteAll = false;

        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = Objects.requireNonNull(tableProperties, "Table data must not be null");
        }

        public DeleteQuery.Builder<T> deleteAll(boolean isDeleteAll) {
            this.isDeleteAll = isDeleteAll;
            return this;
        }

        public DeleteQuery.Builder<T> where(T where, ReflectionContainer<T> reflectionContainer) {
            this.where = IndexedSQLMap.Factory.create(where, tableProperties, reflectionContainer, ColumnProperties::primaryKey);
            return this;
        }

        public DeleteQuery.Builder<T> where(IndexedSQLMap where) {
            this.where = where;
            return this;
        }

        public DeleteQuery<T> build() {
            return new DeleteQuery<>(tableProperties, Objects.requireNonNull(where, "Values must be set"), isDeleteAll);
        }
    }
}
