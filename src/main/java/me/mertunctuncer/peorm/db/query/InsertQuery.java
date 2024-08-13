package me.mertunctuncer.peorm.db.query;


import me.mertunctuncer.peorm.reflection.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.SQLMap;
import org.jetbrains.annotations.NotNull;


public final class InsertQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final SQLMap entry;

    private InsertQuery(@NotNull TableProperties<T> tableProperties, @NotNull SQLMap entryValues) {
        this.tableProperties = tableProperties;
        this.entry = entryValues;
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public SQLPairList getEntry() {
        return entry;
    }

    public static final class Builder<T> implements QueryBuilder<T> {

        private TableProperties<T> tableProperties;
        private SQLPairList entryValues;

        @Override
        public Builder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public Builder<T> withEntryValuesFromInstance(T instance, ReflectionContainer<T> reflectionContainer) {
            tableProperties.columns().stream().map()
            this.entryValues = new SQLMap()
            this.entryValues = SQLPairList.Factory.create(instance, tableProperties, reflectionContainer, columnProperties -> columnProperties.autoIncrement() != null);
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
