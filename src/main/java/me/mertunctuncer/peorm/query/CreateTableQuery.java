package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

public final class CreateTableQuery<T> implements Query<T> {
    private final TableData<T> tableData;
    private final boolean ifNotExists;

    private CreateTableQuery(TableData<T> tableData, boolean ifNotExists) {
        this.tableData = tableData;
        this.ifNotExists = ifNotExists;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;
        private boolean ifNotExists = false;

        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public Builder<T> setIfNotExists(boolean ifNotExists) {
            this.ifNotExists = ifNotExists;
            return this;
        }

        public CreateTableQuery<T> build() {
            return new CreateTableQuery<>(tableData, ifNotExists);
        }
    }
}
