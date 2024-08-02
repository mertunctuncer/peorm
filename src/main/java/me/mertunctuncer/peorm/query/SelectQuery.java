package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.Objects;

public final class SelectQuery<T> implements Query<T> {

    private final TableProperties<T> tableProperties;
    private final SQLPairList whereConstraints;
    private final boolean isFetchAll;

    private SelectQuery(TableProperties<T> tableProperties, SQLPairList whereConstraints) {
        this.tableProperties = Objects.requireNonNull(tableProperties, "tableProperties must not be null");
        this.isFetchAll = whereConstraints == null;
        this.whereConstraints = whereConstraints;
    }

    @Override
    public TableProperties<T> getTableProperties() {
        return tableProperties;
    }

    public SQLPairList getWhereConstraints() {
        return whereConstraints;
    }

    public boolean isFetchAll() {
        return isFetchAll;
    }

    public static final class Builder<T> implements QueryBuilder<T>{

        private TableProperties<T> tableProperties;
        private SQLPairList whereConstraints;
        private boolean isFetchAll = false;

        @Override
        public QueryBuilder<T> withTableProperties(TableProperties<T> tableProperties) {
            this.tableProperties = tableProperties;
            return this;
        }

        public SelectQuery.Builder<T> shouldFetchAll(boolean isFetchAll) {
            this.isFetchAll = isFetchAll;
            return this;
        }

        public SelectQuery.Builder<T> withWhereConstraintByPrimaryKey(T primaryKeyOwner, ReflectionContainer<T> reflectionContainer) {
            this.whereConstraints = SQLPairList.Factory.create(primaryKeyOwner, tableProperties, reflectionContainer, ColumnProperties::primaryKey);
            return this;
        }

        public SelectQuery.Builder<T> withWhereConstraints(SQLPairList whereConstraints) {
            this.whereConstraints = whereConstraints;
            return this;
        }

        public SelectQuery<T> build() {
            if(isFetchAll) whereConstraints = null;
            return new SelectQuery<>(tableProperties, whereConstraints);
        }
    }
}
