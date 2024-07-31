package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.query.CreateTableQuery;
import me.mertunctuncer.peorm.query.DropTableQuery;
import me.mertunctuncer.peorm.query.InsertQuery;
import me.mertunctuncer.peorm.query.Query;
import me.mertunctuncer.peorm.reflection.ClassParser;
import me.mertunctuncer.peorm.reflection.InstanceFactory;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TableAccessProviderImpl<T> implements TableAccessProvider<T> {

    private final DatabaseController databaseController;
    private final ReflectionData<T> reflectionData;
    private final InstanceFactory<T> instanceFactory;
    private final TableData<T> tableData;


    public TableAccessProviderImpl(DatabaseController databaseController, Class<T> clazz) {
        this(databaseController, clazz, null);
    }

    public TableAccessProviderImpl(
            DatabaseController databaseController,
            Class<T> clazz,
            T instance
    ) {
        ClassParser<T> parser = new ClassParser<>(clazz);

        if(instance != null) parser.setDefaults(instance);

        this.databaseController = databaseController;
        this.tableData = parser.getTableData();
        this.reflectionData = parser.getReflectionData();
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
        Query<T> query = new CreateTableQuery.Builder<>(tableData)
                .setIfNotExists(false)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean create(boolean ifNotExists) {
        Query<T> query = new CreateTableQuery.Builder<>(tableData)
                .setIfNotExists(ifNotExists)
                .build();
        return databaseController.execute(query).isSuccessful();
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
        Query<T> query = new DropTableQuery<>.Builder<>(tableData).build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> dropAsync() {
        return CompletableFuture.supplyAsync(this::drop, databaseController.getExecutorService());
    }

    @Override
    public boolean insert(T data) {
        Query<T> query = new InsertQuery.Builder<>(tableData)
                .setValues(data, reflectionData)
                .build();

        return databaseController.execute(query).isSuccessful();
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
    public boolean update(T object, IndexedSQLMap where) {
        return false;
    }

    @Override
    public boolean update(IndexedSQLMap set, IndexedSQLMap where) {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T object) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T object, IndexedSQLMap where) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(IndexedSQLMap set, IndexedSQLMap where) {
        return null;
    }

    @Override
    public List<T> fetch(T object) {
        return List.of();
    }

    @Override
    public List<T> fetch(IndexedSQLMap where) {
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
    public CompletableFuture<List<T>> fetchAsync(IndexedSQLMap where) {
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
    public boolean delete(IndexedSQLMap where) {
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
    public CompletableFuture<Boolean> deleteAsync(IndexedSQLMap where) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteAllAsync() {
        return null;
    }
}
