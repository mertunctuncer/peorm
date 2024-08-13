package me.mertunctuncer.peorm.db.dao;

import java.util.concurrent.ExecutorService;

public interface DAOFactory {
    <T> DataAccessObject<T> create(Class<T> clazz);
    <T> DataAccessObject<T> create(Class<T> clazz, ExecutorService executorService);
}