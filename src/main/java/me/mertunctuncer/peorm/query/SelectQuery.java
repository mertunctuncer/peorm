package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.Objects;
import java.util.function.Predicate;

public final class SelectQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final IndexedSQLMap whereData;
    private final boolean isFetchAll;

    private SelectQuery(TableProperties<T> tableProperties, IndexedSQLMap whereData, boolean isFetchAll) {
        this.tableProperties = tableProperties;
        this.isFetchAll = isFetchAll;
        if(!isFetchAll) this.whereData = Objects.requireNonNull(whereData, "Where must not be null if the query is not fetch all");
        else this.whereData = whereData;
    }

    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public IndexedSQLMap getWhereData() {
        return whereData;
    }

    public boolean isFetchAll() {
        return isFetchAll;
    }

    public static final class Builder<T> {

        private final TableProperties<T> tableProperties;
        private IndexedSQLMap selectData;
        private boolean isFetchAll = false;


        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
        }
        public SelectQuery.Builder<T> fetchAll(boolean isFetchAll) {
            this.isFetchAll = isFetchAll;
            return this;
        }

        public SelectQuery.Builder<T> where(T where, ReflectionContainer<T> reflectionContainer) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableProperties, reflectionContainer);
            return this;
        }

        public SelectQuery.Builder<T> where(T where, ReflectionContainer<T> reflectionContainer, Predicate<ColumnProperties> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableProperties, reflectionContainer, allowFilter);
            return this;
        }

        public SelectQuery.Builder<T> where(IndexedSQLMap where) {
            this.selectData = where;
            return this;
        }

        public SelectQuery<T> build() {
            return new SelectQuery<>(
                    tableProperties,
                    Objects.requireNonNull(selectData, "Select data must be set"),
                    isFetchAll
            );
        }
    }
}
