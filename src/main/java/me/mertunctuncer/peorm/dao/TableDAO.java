package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.query.CreateTableQuery;
import me.mertunctuncer.peorm.query.DropTableQuery;
import me.mertunctuncer.peorm.query.InsertQuery;
import me.mertunctuncer.peorm.reflection.ClassParser;
import me.mertunctuncer.peorm.reflection.InstanceFactory;
import me.mertunctuncer.peorm.util.SQLSet;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TableDAO<T> implements TableAccessProvider<T> {

    private final DatabaseController databaseController;
    private final InstanceFactory<T> instanceFactory;
    private final TableData<T> tableData;


    public TableDAO(DatabaseController databaseController, Class<T> clazz) {
        this(databaseController, clazz, null);
    }

    public TableDAO(
            DatabaseController databaseController,
            Class<T> clazz,
            T instance
    ) {
        ClassParser<T> parser = new ClassParser<>(clazz);

        if(instance != null) parser.useDefaultsFrom(instance);

        this.databaseController = databaseController;
        this.tableData = parser.getTableData();
        this.instanceFactory = parser.createInstanceFactory();
    }

    @Override
    public boolean fetchExists() {
        return databaseController
                .tableExists(tableData.name());
    }

    @Override
    public CompletableFuture<Boolean> fetchExistsAsync() {
        return CompletableFuture.supplyAsync(this::fetchExists, databaseController.getExecutorService());
    }

    @Override
    public boolean create() {
        return databaseController.execute(new CreateTableQuery<>(tableData, false)).isSuccessful();
    }

    @Override
    public boolean create(boolean ifNotExists) {
        return databaseController.execute(new CreateTableQuery<>(tableData, ifNotExists)).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> createAsync() {
        return CompletableFuture.supplyAsync(this::create, databaseController.getExecutorService());
    }

    @Override
    public CompletableFuture<Boolean> createAsync(boolean ifNotExists) {
        return CompletableFuture.supplyAsync(() -> create(ifNotExists), databaseController.getExecutorService());
    }

    @Override
    public boolean drop() {
        return databaseController.execute(new DropTableQuery<>(tableData)).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> dropAsync() {
        return CompletableFuture.supplyAsync(this::drop, databaseController.getExecutorService());
    }

    @Override
    public boolean insert(T object) {
        return databaseController.execute(new InsertQuery<>(object, tableData)).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> insertAsync(T object) {
        return CompletableFuture.supplyAsync(() -> insert(object), databaseController.getExecutorService());
    }

    @Override
    public boolean upsert(T object) {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> upsertAsync(T object) {
        return null;
    }

    @Override
    public boolean update(T object) {
        return false;
    }

    @Override
    public boolean update(T object, SQLSet where) {
        return false;
    }

    @Override
    public boolean update(SQLSet set, SQLSet where) {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T object) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T object, SQLSet where) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(SQLSet set, SQLSet where) {
        return null;
    }

    @Override
    public List<T> fetch(T object) {
        return List.of();
    }

    @Override
    public List<T> fetch(SQLSet where) {
        return List.of();
    }

    @Override
    public List<T> fetchAll() {
        return List.of();
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(T object) {
        return null;
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(SQLSet where) {
        return null;
    }

    @Override
    public CompletableFuture<List<T>> fetchAllAsync() {
        return null;
    }

    @Override
    public boolean delete(T object) {
        return false;
    }

    @Override
    public boolean delete(SQLSet where) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(T object) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(SQLSet where) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteAllAsync() {
        return null;
    }
}
