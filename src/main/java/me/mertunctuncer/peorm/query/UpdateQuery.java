package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.IndexedSQLMap;


import java.util.Objects;
import java.util.function.Predicate;

public final class UpdateQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final IndexedSQLMap where;
    private final IndexedSQLMap updateData;

    private UpdateQuery(TableProperties<T> tableProperties, IndexedSQLMap where, IndexedSQLMap rowData) {
        this.tableProperties = tableProperties;
        this.where = where;
        this.updateData = rowData;
    }

    @Override
    public TableProperties<T> getTableData() {
        return tableProperties;
    }

    public IndexedSQLMap getWhereData() {
        return where;
    }

    public IndexedSQLMap getRowData() {
        return updateData;
    }

    public static final class Builder<T> {

        private final TableProperties<T> tableProperties;
        private IndexedSQLMap selectData;
        private IndexedSQLMap updateData;


        public Builder(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
        }

        public UpdateQuery.Builder<T> where(T where, ReflectionContainer<T> reflectionContainer) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableProperties, reflectionContainer);
            return this;
        }

        public UpdateQuery.Builder<T> where(T where, ReflectionContainer<T> reflectionContainer, Predicate<ColumnProperties> allowFilter) {
            this.selectData = IndexedSQLMap.Factory.create(where, tableProperties, reflectionContainer, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> where(IndexedSQLMap where) {
            this.selectData = where;
            return this;
        }

        public UpdateQuery.Builder<T> rowData(T rowData, ReflectionContainer<T> reflectionContainer) {
            this.updateData = IndexedSQLMap.Factory.create(rowData, tableProperties, reflectionContainer);
            return this;
        }

        public UpdateQuery.Builder<T> rowData(T rowData, ReflectionContainer<T> reflectionContainer, Predicate<ColumnProperties> allowFilter) {
            this.updateData = IndexedSQLMap.Factory.create(rowData, tableProperties, reflectionContainer, allowFilter);
            return this;
        }

        public UpdateQuery.Builder<T> rowData(IndexedSQLMap rowData) {
            this.updateData = rowData;
            return this;
        }

        public UpdateQuery<T> build() {
            return new UpdateQuery<>(
                    tableProperties,
                    Objects.requireNonNull(selectData, "Select data must be set"),
                    Objects.requireNonNull(updateData, "Update data must be set")
            );
        }
    }
}
