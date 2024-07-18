package me.mertunctuncer.peorm.api.dao;

import me.mertunctuncer.peorm.api.util.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;

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
    boolean update(T object, Pair<String, Object> where);
    boolean update(T object, List<Pair<String, Object>> where);

    boolean update(Pair<String, Object> set, Pair<String, Object> where);

    CompletableFuture<Boolean> updateAsync(T object);
    CompletableFuture<Boolean> updateAsync(T object, Pair<String, Object> where);
    CompletableFuture<Boolean> updateAsync(T object, List<Pair<String, Object>> where);



}
