package me.mertunctuncer.peorm.db.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.reflection.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.Objects;

public final class UpdateQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final SQLPairList whereConstraints;
    private final SQLPairList newValues;

    private UpdateQuery(TableProperties<T> tableProperties, SQLPairList whereConstraints, SQLPairList newValues) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
        this.whereConstraints = whereConstraints;
        this.newValues = newValues;
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public SQLPairList getWhereConstraints() {
        return whereConstraints;
    }

    public SQLPairList getNewValues() {
        return newValues;
    }

    public static final class Builder<T> implements QueryBuilder<T> {

        private TableProperties<T> tableProperties;
        private SQLPairList whereConstraints;
        private SQLPairList newValues;

        @Override
        public Builder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public Builder<T> withWhereConstraintsByPrimaryKey(T primaryKeyOwner, ReflectionContainer<T> reflectionContainer) {
            this.whereConstraints = SQLPairList.Factory.create(primaryKeyOwner, tableProperties, reflectionContainer, ColumnProperties::primaryKey);
            return this;
        }

        public Builder<T> withWhereConstraints(SQLPairList whereConstraints) {
            this.whereConstraints = whereConstraints;
            return this;
        }

        public Builder<T> withNewValuesFromInstance(T instance, ReflectionContainer<T> reflectionContainer) {
            this.newValues = SQLPairList.Factory.create(instance, tableProperties, reflectionContainer);
            return this;
        }

        public Builder<T> withNewValues(SQLPairList newValues) {
            this.newValues = newValues;
            return this;
        }

        public UpdateQuery<T> build() {
            return new UpdateQuery<>(
                    tableProperties,
                    whereConstraints,
                    newValues
            );
        }
    }
}
