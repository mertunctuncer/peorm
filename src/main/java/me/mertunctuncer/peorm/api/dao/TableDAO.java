package me.mertunctuncer.peorm.api.dao;

import me.mertunctuncer.peorm.api.util.SQLSet;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TableDAO<T> implements TableAccessProvider<T>{
    @Override
    public boolean fetchExists() {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> fetchExistsAsync() {
        return null;
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> createAsync() {
        return null;
    }

    @Override
    public boolean drop() {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> dropAsync() {
        return null;
    }

    @Override
    public boolean insert(T object) {
        return false;
    }

    @Override
    public CompletableFuture<Boolean> insertAsync(T object) {
        return null;
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
    public Set<T> fetch(T object) {
        return Set.of();
    }

    @Override
    public Set<T> fetch(SQLSet where) {
        return Set.of();
    }

    @Override
    public Set<T> fetchAll() {
        return Set.of();
    }

    @Override
    public CompletableFuture<Set<T>> fetchAsync(T object) {
        return null;
    }

    @Override
    public CompletableFuture<Set<T>> fetchAsync(SQLSet where) {
        return null;
    }

    @Override
    public CompletableFuture<Set<T>> fetchAllAsync() {
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
