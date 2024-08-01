package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.model.ColumnData;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.query.*;
import me.mertunctuncer.peorm.reflection.ClassParser;
import me.mertunctuncer.peorm.reflection.InstanceFactory;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class TableAccessProviderImpl<T> implements TableAccessProvider<T> {

    private final DatabaseController databaseController;
    private final ExecutorService executorService;
    private final ReflectionData<T> reflectionData;
    private final InstanceFactory<T> instanceFactory;
    private final TableData<T> tableData;


    public TableAccessProviderImpl(DatabaseController databaseController, Class<T> clazz, ExecutorService executorService) {
        this(databaseController, clazz, executorService, null);
    }

    public TableAccessProviderImpl(
            DatabaseController databaseController,
            Class<T> clazz,
            ExecutorService executorService,
            T instance
    ) {
        ClassParser<T> parser = new ClassParser<>(clazz);

        if(instance != null) parser.setDefaults(instance);

        this.databaseController = databaseController;
        this.tableData = parser.getTableData();
        this.reflectionData = parser.getReflectionData();
        this.executorService = executorService;
        this.instanceFactory = parser.createInstanceFactory();
    }

    @Override
    public boolean fetchExists() {
        return databaseController
                .tableExists(tableData.name());
    }

    @Override
    public CompletableFuture<Boolean> fetchExistsAsync() {
        return CompletableFuture.supplyAsync(this::fetchExists, executorService);
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
        return CompletableFuture.supplyAsync(this::create, executorService);
    }

    @Override
    public CompletableFuture<Boolean> createAsync(boolean ifNotExists) {
        return CompletableFuture.supplyAsync(() -> create(ifNotExists), executorService);
    }

    @Override
    public boolean drop() {
        Query<T> query = new DropTableQuery<>.Builder<>(tableData).build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> dropAsync() {
        return CompletableFuture.supplyAsync(this::drop, executorService);
    }

    @Override
    public boolean insert(T data) {
        Query<T> query = new InsertQuery.Builder<>(tableData)
                .rowData(data, reflectionData)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> insertAsync(T data) {
        return CompletableFuture.supplyAsync(() -> insert(data), executorService);
    }

    @Override
    public boolean upsert(T data) {
        Query<T> query = new UpsertQuery.Builder<>(tableData)
                .rowData(data, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> upsertAsync(T data) {
        return CompletableFuture.supplyAsync(() -> update(data), executorService);
    }

    @Override
    public boolean update(T data) {
        Query<T> query = new UpdateQuery.Builder<>(tableData)
                .where(data, reflectionData, ColumnData::primaryKey)
                .rowData(data, reflectionData, columnData -> !columnData.primaryKey())
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(T data, IndexedSQLMap where) {
        Query<T> query = new UpdateQuery.Builder<>(tableData)
                .where(where)
                .rowData(data, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(IndexedSQLMap data, IndexedSQLMap where) {
        Query<T> query = new UpdateQuery.Builder<>(tableData)
                .where(where)
                .rowData(data)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T data) {
        return CompletableFuture.supplyAsync(() -> update(data), executorService);
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T data, IndexedSQLMap where) {
        return CompletableFuture.supplyAsync(() -> update(data, where), executorService);
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(IndexedSQLMap data, IndexedSQLMap where) {
        return CompletableFuture.supplyAsync(() -> update(data, where), executorService);
    }

    @Override
    public List<T> fetch(T where) {
        Query<T> query = new SelectQuery.Builder<>(tableData)
                .where(where, reflectionData, ColumnData::primaryKey)
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public List<T> fetch(IndexedSQLMap where) {
        Query<T> query = new SelectQuery.Builder<>(tableData)
                .where(where)
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public List<T> fetchAll() {
        Query<T> query = new SelectQuery.Builder<>(tableData)
                .fetchAll()
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(T where) {
        return CompletableFuture.supplyAsync(() -> fetch(where), executorService);
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(IndexedSQLMap where) {
        return CompletableFuture.supplyAsync(() -> fetch(where), executorService);
    }

    @Override
    public CompletableFuture<List<T>> fetchAllAsync() {
        return CompletableFuture.supplyAsync(this::fetchAll, executorService);
    }

    @Override
    public boolean delete(T where) {
        Query<T> query = new DeleteQuery.Builder<>(tableData)
                .where(where, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean delete(IndexedSQLMap where) {
        Query<T> query = new DeleteQuery.Builder<>(tableData)
                .where(where)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean deleteAll() {
        Query<T> query = new DeleteQuery.Builder<>(tableData)
                .deleteAll(true)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(T where) {
        return CompletableFuture.supplyAsync(() -> delete(where), executorService);
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(IndexedSQLMap where) {
        return CompletableFuture.supplyAsync(() -> delete(where), executorService);
    }

    @Override
    public CompletableFuture<Boolean> deleteAllAsync() {
        return CompletableFuture.supplyAsync(this::deleteAll, executorService);
    }
}
