package me.mertunctuncer.peorm.api.dao;

import me.mertunctuncer.peorm.api.util.SQLSet;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface TableAccessProvider <T> {

    boolean fetchExists();
    CompletableFuture<Boolean> fetchExistsAsync();

    boolean create();
    CompletableFuture<Boolean> createAsync();

    boolean drop();
    CompletableFuture<Boolean> dropAsync();

    boolean insert(T object);
    CompletableFuture<Boolean> insertAsync(T object);

    boolean upsert(T object);
    CompletableFuture<Boolean> upsertAsync(T object);

    boolean update(T object);
    boolean update(T object, SQLSet where);
    boolean update(SQLSet set, SQLSet where);

    CompletableFuture<Boolean> updateAsync(T object);
    CompletableFuture<Boolean> updateAsync(T object, SQLSet where);
    CompletableFuture<Boolean> updateAsync(SQLSet set, SQLSet where);

    Set<T> fetch(T object);
    Set<T> fetch(SQLSet where);
    Set<T> fetchAll();

    CompletableFuture<Set<T>> fetchAsync(T object);
    CompletableFuture<Set<T>> fetchAsync(SQLSet where);
    CompletableFuture<Set<T>> fetchAllAsync();

    boolean delete(T object);
    boolean delete(SQLSet where);
    boolean deleteAll();

    CompletableFuture<Boolean> deleteAsync(T object);
    CompletableFuture<Boolean> deleteAsync(SQLSet where);
    CompletableFuture<Boolean> deleteAllAsync();
}
