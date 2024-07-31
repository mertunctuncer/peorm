package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TableAccessProvider <T> {

    boolean fetchExists();
    CompletableFuture<Boolean> fetchExistsAsync();

    boolean create();
    boolean create(boolean ifNotExists);
    CompletableFuture<Boolean> createAsync();
    CompletableFuture<Boolean> createAsync(boolean ifNotExists);

    boolean drop();
    CompletableFuture<Boolean> dropAsync();

    boolean insert(T object);
    CompletableFuture<Boolean> insertAsync(T object);

    boolean upsert(T object);
    CompletableFuture<Boolean> upsertAsync(T object);

    boolean update(T object);
    boolean update(T object, IndexedSQLMap where);
    boolean update(IndexedSQLMap set, IndexedSQLMap where);

    CompletableFuture<Boolean> updateAsync(T object);
    CompletableFuture<Boolean> updateAsync(T object, IndexedSQLMap where);
    CompletableFuture<Boolean> updateAsync(IndexedSQLMap set, IndexedSQLMap where);

    List<T> fetch(T object);
    List<T> fetch(IndexedSQLMap where);
    List<T> fetchAll();

    CompletableFuture<List<T>> fetchAsync(T object);
    CompletableFuture<List<T>> fetchAsync(IndexedSQLMap where);
    CompletableFuture<List<T>> fetchAllAsync();

    boolean delete(T object);
    boolean delete(IndexedSQLMap where);
    boolean deleteAll();

    CompletableFuture<Boolean> deleteAsync(T object);
    CompletableFuture<Boolean> deleteAsync(IndexedSQLMap where);
    CompletableFuture<Boolean> deleteAllAsync();
}
