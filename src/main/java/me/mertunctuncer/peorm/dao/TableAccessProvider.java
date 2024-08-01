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

    boolean insert(T data);
    CompletableFuture<Boolean> insertAsync(T data);

    boolean upsert(T data);
    CompletableFuture<Boolean> upsertAsync(T data);

    boolean update(T data);
    boolean update(T data, IndexedSQLMap where);
    boolean update(IndexedSQLMap data, IndexedSQLMap where);

    CompletableFuture<Boolean> updateAsync(T data);
    CompletableFuture<Boolean> updateAsync(T data, IndexedSQLMap where);
    CompletableFuture<Boolean> updateAsync(IndexedSQLMap data, IndexedSQLMap where);

    List<T> fetch(T where);
    List<T> fetch(IndexedSQLMap where);
    List<T> fetchAll();

    CompletableFuture<List<T>> fetchAsync(T where);
    CompletableFuture<List<T>> fetchAsync(IndexedSQLMap where);
    CompletableFuture<List<T>> fetchAllAsync();

    boolean delete(T where);
    boolean delete(IndexedSQLMap where);
    boolean deleteAll();

    CompletableFuture<Boolean> deleteAsync(T where);
    CompletableFuture<Boolean> deleteAsync(IndexedSQLMap where);
    CompletableFuture<Boolean> deleteAllAsync();
}
