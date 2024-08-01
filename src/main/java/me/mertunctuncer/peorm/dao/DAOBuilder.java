package me.mertunctuncer.peorm.dao;

import me.mertunctuncer.peorm.db.DatabaseController;
import me.mertunctuncer.peorm.reflection.ClassParser;

import java.util.concurrent.ExecutorService;

public class DAOBuilder<T> {

    private final Class<T> clazz;
    private final DatabaseController controller;
    private ExecutorService executorService = null;
    private T defaults = null;

    public DAOBuilder(Class<T> clazz, DatabaseController controller) {
        this.clazz = clazz;
        this.controller = controller;
    }

    public DAOBuilder<T> setDefaults(T defaults) {
        this.defaults = defaults;
        return this;
    }

    public DAOBuilder<T> setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public DAO<T> build() {
        ClassParser<T> parser = new ClassParser<>(clazz);

        if(defaults != null) parser.setDefaults(defaults);
        if(executorService == null) executorService = controller.getExecutor();

        DAO<T> dao = new DAOImpl<>(
                parser.getTableData(),
                controller,
                executorService,
                parser.getReflectionData(),
                parser.createInstanceFactory()
        );

        controller.register(clazz, dao);
        return dao;
    }
}
