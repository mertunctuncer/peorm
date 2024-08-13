package me.mertunctuncer.peorm.db.dao;

import me.mertunctuncer.peorm.util.SQLMap;

import java.util.List;

public interface TableAccessObject<T> extends AutoCloseable{

    boolean insert(T entry);

    boolean upsert(T entry);

    boolean update(T entry);
    boolean update(T entry, SQLMap whereConstraints);
    boolean update(SQLMap entryValues, SQLMap whereConstraints);

    List<T> fetch(T primaryKeyOwner);
    List<T> fetch(SQLMap whereConstraints);
    List<T> fetchAll();

    boolean delete(T primaryKeyOwner);
    boolean delete(SQLMap whereConstraints);
    boolean deleteAll();
}
