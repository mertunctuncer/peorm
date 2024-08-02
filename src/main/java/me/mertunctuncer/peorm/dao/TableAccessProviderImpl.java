package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
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
    private final ReflectionContainer<T> reflectionContainer;
    private final InstanceFactory<T> instanceFactory;
    private final TableProperties<T> tableProperties;


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
        this.tableProperties = parser.getTableProperties();
        this.reflectionContainer = parser.getReflectionContainer();
        this.executorService = executorService;
        this.instanceFactory = parser.createInstanceFactory();
    }

    @Override
    public boolean fetchExists() {
        return databaseController
                .tableExists(tableProperties.name());
    }

    @Override
    public CompletableFuture<Boolean> fetchExistsAsync() {
        return CompletableFuture.supplyAsync(this::fetchExists, executorService);
    }

    @Override
    public boolean create() {
        Query<T> query = new CreateTableQuery.Builder<>(tableProperties)
                .setIfNotExists(false)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean create(boolean ifNotExists) {
        Query<T> query = new CreateTableQuery.Builder<>(tableProperties)
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
        Query<T> query = new DropTableQuery<>.Builder<>(tableProperties).build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> dropAsync() {
        return CompletableFuture.supplyAsync(this::drop, executorService);
    }

    @Override
    public boolean insert(T data) {
        Query<T> query = new InsertQuery.Builder<>(tableProperties)
                .rowData(data, reflectionContainer)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> insertAsync(T data) {
        return CompletableFuture.supplyAsync(() -> insert(data), executorService);
    }

    @Override
    public boolean upsert(T data) {
        Query<T> query = new UpsertQuery.Builder<>(tableProperties)
                .rowData(data, reflectionContainer)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> upsertAsync(T data) {
        return CompletableFuture.supplyAsync(() -> update(data), executorService);
    }

    @Override
    public boolean update(T data) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
                .where(data, reflectionContainer, ColumnProperties::primaryKey)
                .rowData(data, reflectionContainer, columnData -> !columnData.primaryKey())
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(T data, IndexedSQLMap where) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
                .where(where)
                .rowData(data, reflectionContainer)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(IndexedSQLMap data, IndexedSQLMap where) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
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
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .where(where, reflectionContainer, ColumnProperties::primaryKey)
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::createWithOverrides).toList();
    }

    @Override
    public List<T> fetch(IndexedSQLMap where) {
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .where(where)
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::createWithOverrides).toList();
    }

    @Override
    public List<T> fetchAll() {
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .fetchAll(true)
                .build();
        List<IndexedSQLMap> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::createWithOverrides).toList();
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
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
                .where(where, reflectionContainer)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean delete(IndexedSQLMap where) {
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
                .where(where)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean deleteAll() {
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
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
