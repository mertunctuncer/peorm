package me.mertunctuncer.peorm.query;


import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.Objects;


public final class InsertQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final SQLPairList entryValues;

    private InsertQuery(TableProperties<T> tableProperties, SQLPairList entryValues) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
        this.entryValues = Objects.requireNonNull(entryValues, "entryValues must not be null");
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public SQLPairList getEntryValues() {
        return entryValues;
    }

    public static final class Builder<T> implements QueryBuilder<T> {

        private TableProperties<T> tableProperties;
        private SQLPairList entryValues;

        @Override
        public QueryBuilder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public Builder<T> withEntryValuesFromInstance(T instance, ReflectionContainer<T> reflectionContainer) {
            this.entryValues = SQLPairList.Factory.create(instance, tableProperties, reflectionContainer, columnData -> columnData.autoIncrement() != null);
            return this;
        }

        public Builder<T> withEntryValues(SQLPairList entryValues) {
            this.entryValues = entryValues;
            return this;
        }

        public InsertQuery<T> build() {
            return new InsertQuery<>(tableProperties, entryValues);
        }
    }
}
