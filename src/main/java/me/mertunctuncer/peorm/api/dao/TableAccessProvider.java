package me.mertunctuncer.peorm.api.dao;

public interface TableAccessProvider<T> {

    void createTable();
    void dropTable();
    void insert(T t);
    void upsert(T t);
    void update(T t, Pair<>);
    void delete(T t);
}
