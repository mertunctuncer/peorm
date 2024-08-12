package me.mertunctuncer.peorm.db.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.db.query.*;
import me.mertunctuncer.peorm.reflection.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.reflection.InstanceFactory;
import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class DataAccessObjectImpl<T> implements DataAccessObject<T> {

    private final DatabaseController databaseController;
    private final ExecutorService executorService;
    private final ReflectionContainer<T> reflectionContainer;
    private final InstanceFactory<T> instanceFactory;
    private final TableProperties<T> tableProperties;

    DataAccessObjectImpl(
            TableProperties<T> tableProperties,
            DatabaseController databaseController,
            ExecutorService executorService,
            ReflectionContainer<T> reflectionContainer,
            InstanceFactory<T> instanceFactory
    ) {
        this.tableProperties = tableProperties;
        this.databaseController = databaseController;
        this.executorService = executorService;
        this.reflectionContainer = reflectionContainer;
        this.instanceFactory = instanceFactory;
    }

    @Override
    public boolean fetchExists() {
        return databaseController.fetchTableExists(tableProperties.name()).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> fetchExistsAsync() {
        return CompletableFuture.supplyAsync(this::fetchExists, executorService);
    }

    @Override
    public boolean create() {
        Query<T> query = new CreateTableQuery.Builder<T>()
                .withTableProperties(tableProperties)
                .withIfNotExists(false)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean create(boolean ifNotExists) {
        CreateTableQuery.QueryBuilder<T> builder = CreateTableQuery.builder(tableProperties);
        Query<T> query = builder
                .withIfNotExists(ifNotExists)
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
    public boolean insert(T entry) {
        Query<T> query = new InsertQuery.Builder<>(tableProperties)
                .withEntryValuesFromInstance(entry, reflectionData)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> insertAsync(T entry) {
        return CompletableFuture.supplyAsync(() -> insert(entry), executorService);
    }

    @Override
    public boolean upsert(T entry) {
        Query<T> query = new UpsertQuery.Builder<>(tableProperties)
                .withNewValuesFromInstance(entry, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> upsertAsync(T entry) {
        return CompletableFuture.supplyAsync(() -> update(entry), executorService);
    }

    @Override
    public boolean update(T entry) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
                .where(entry, reflectionData, ColumnData::primaryKey)
                .rowData(entry, reflectionData, columnData -> !columnData.primaryKey())
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(T entry, SQLPairList whereConstraints) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
                .withWhereConstraints(whereConstraints)
                .withNewValuesFromInstance(entry, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean update(SQLPairList entryValues, SQLPairList whereConstraints) {
        Query<T> query = new UpdateQuery.Builder<>(tableProperties)
                .withWhereConstraints(whereConstraints)
                .withNewValues(entryValues)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T entry) {
        return CompletableFuture.supplyAsync(() -> update(entry), executorService);
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(T entry, SQLPairList whereConstraints) {
        return CompletableFuture.supplyAsync(() -> update(entry, whereConstraints), executorService);
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(SQLPairList newValues, SQLPairList whereConstraints) {
        return CompletableFuture.supplyAsync(() -> update(newValues, whereConstraints), executorService);
    }

    @Override
    public List<T> fetch(T primaryKeyOwner) {
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .withWhereConstraints(primaryKeyOwner, reflectionData, ColumnData::primaryKey)
                .build();
        List<SQLPairList> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public List<T> fetch(SQLPairList whereConstraints) {
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .withWhereConstraints(whereConstraints)
                .build();
        List<SQLPairList> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public List<T> fetchAll() {
        Query<T> query = new SelectQuery.Builder<>(tableProperties)
                .shouldFetchAll(true)
                .build();
        List<SQLPairList> results = databaseController.fetch(query).getResults();
        return results.stream().map(instanceFactory::initialize).toList();
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(T primaryKeyOwner) {
        return CompletableFuture.supplyAsync(() -> fetch(primaryKeyOwner), executorService);
    }

    @Override
    public CompletableFuture<List<T>> fetchAsync(SQLPairList whereConstraints) {
        return CompletableFuture.supplyAsync(() -> fetch(whereConstraints), executorService);
    }

    @Override
    public CompletableFuture<List<T>> fetchAllAsync() {
        return CompletableFuture.supplyAsync(this::fetchAll, executorService);
    }

    @Override
    public boolean delete(T primaryKeyOwner) {
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
                .withWhereConstraintByPrimaryKey(primaryKeyOwner, reflectionData)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean delete(SQLPairList whereConstraints) {
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
                .withWhereConstraintByPrimaryKey(whereConstraints)
                .build();
        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public boolean deleteAll() {
        Query<T> query = new DeleteQuery.Builder<>(tableProperties)
                .shouldDeleteAll(true)
                .build();

        return databaseController.execute(query).isSuccessful();
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(T primaryKeyOwner) {
        return CompletableFuture.supplyAsync(() -> delete(primaryKeyOwner), executorService);
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(SQLPairList whereConstraints) {
        return CompletableFuture.supplyAsync(() -> delete(whereConstraints), executorService);
    }

    @Override
    public CompletableFuture<Boolean> deleteAllAsync() {
        return CompletableFuture.supplyAsync(this::deleteAll, executorService);
    }

    @Override
    public void close() {
        executorService.close();
    }
}
