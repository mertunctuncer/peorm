package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;


public final class DropTableQuery<T> implements Query<T> {

    private final TableData<T> tableData;

    private DropTableQuery(TableData<T> tableData) {
        this.tableData = tableData;
    }

    @Override
    public TableData<T> getTableData() {
        return tableData;
    }

    public static final class Builder<T> {

        private final TableData<T> tableData;

        public Builder(TableData<T> tableData) {
            this.tableData = tableData;
        }

        public DropTableQuery<T> build() {
            return new DropTableQuery<>(tableData);
        }
    }
}
