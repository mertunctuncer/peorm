package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.util.SQLPairList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataAccessObject<T> extends AutoCloseable{

    boolean fetchExists();
    CompletableFuture<Boolean> fetchExistsAsync();

    boolean create();
    boolean create(boolean ifNotExists);
    CompletableFuture<Boolean> createAsync();
    CompletableFuture<Boolean> createAsync(boolean ifNotExists);

    boolean drop();
    CompletableFuture<Boolean> dropAsync();

    boolean insert(T entry);
    CompletableFuture<Boolean> insertAsync(T entry);

    boolean upsert(T entry);
    CompletableFuture<Boolean> upsertAsync(T entry);

    boolean update(T entry);
    boolean update(T entry, SQLPairList whereConstraints);
    boolean update(SQLPairList entryValues, SQLPairList whereConstraints);

    CompletableFuture<Boolean> updateAsync(T entry);
    CompletableFuture<Boolean> updateAsync(T entry, SQLPairList whereConstraints);
    CompletableFuture<Boolean> updateAsync(SQLPairList newValues, SQLPairList whereConstraints);

    List<T> fetch(T primaryKeyOwner);
    List<T> fetch(SQLPairList whereConstraints);
    List<T> fetchAll();

    CompletableFuture<List<T>> fetchAsync(T primaryKeyOwner);
    CompletableFuture<List<T>> fetchAsync(SQLPairList whereConstraints);
    CompletableFuture<List<T>> fetchAllAsync();

    boolean delete(T primaryKeyOwner);
    boolean delete(SQLPairList whereConstraints);
    boolean deleteAll();

    CompletableFuture<Boolean> deleteAsync(T primaryKeyOwner);
    CompletableFuture<Boolean> deleteAsync(SQLPairList whereConstraints);
    CompletableFuture<Boolean> deleteAllAsync();


}
