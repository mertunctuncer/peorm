package me.mertunctuncer.peorm.db.dao;

import java.util.concurrent.ExecutorService;

public interface DAOFactory {
    <T> TableAccessObject<T> create(Class<T> clazz);
    <T> TableAccessObject<T> create(Class<T> clazz, ExecutorService executorService);
}