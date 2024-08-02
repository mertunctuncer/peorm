package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.Objects;

public final class DeleteQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final SQLPairList whereConstraints;
    private final boolean isDeleteAll;

    private DeleteQuery(TableProperties<T> tableProperties, SQLPairList whereConstraints) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
        this.isDeleteAll = whereConstraints == null;
        this.whereConstraints = whereConstraints;
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public SQLPairList getWhereConstraints() {
        return whereConstraints;
    }

    public boolean isDeleteAll() {
        return isDeleteAll;
    }

    public static final class Builder<T> implements QueryBuilder<T>{

        private TableProperties<T> tableProperties;
        private SQLPairList whereConstraints = null;
        private boolean isDeleteAll = false;

        @Override
        public Builder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public Builder<T> shouldDeleteAll(boolean isDeleteAll) {
            this.isDeleteAll = isDeleteAll;
            return this;
        }

        public Builder<T> withWhereConstraintByPrimaryKey(T primaryKeyOwner, ReflectionContainer<T> reflectionContainer) {
            this.whereConstraints = SQLPairList.Factory.create(primaryKeyOwner, tableProperties, reflectionContainer, ColumnProperties::primaryKey);
            return this;
        }

        public Builder<T> withWhereConstraint(SQLPairList whereClause) {
            this.whereConstraints = whereClause;
            return this;
        }

        public DeleteQuery<T> build() {
            if (isDeleteAll) whereConstraints = null;
            return new DeleteQuery<>(tableProperties, whereConstraints);
        }
    }
}
