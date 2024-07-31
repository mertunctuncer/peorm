package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.util.IndexedSQLMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

public class InstanceFactory<T> {

    private final Class<T> clazz;
    private final Map<String, Field> columnFieldMap;
    private final Map<Field, Object> defaults;

    public InstanceFactory(ReflectionData<T> reflectionData, Map<Field, Object> defaults) {
        this.clazz = reflectionData.getClazz();
        this.columnFieldMap = reflectionData.getColumnFieldMap();
        this.defaults = defaults;
    }

    public T initializeDefault() {

        T instance = initializeEmpty();

        for(Map.Entry<String, Field> entry : columnFieldMap.entrySet()) {
            Field field = entry.getValue();
            Object value = defaults.get(field);
            try {
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public T initialize(IndexedSQLMap fieldValueOverrides) {
        Objects.requireNonNull(fieldValueOverrides, "Overrides must not be null");

        T instance = initializeEmpty();

        for(Map.Entry<String, Field> entry : columnFieldMap.entrySet()) {
            Field field = entry.getValue();
            Object value = fieldValueOverrides.getValueOrDefault(entry.getKey(), defaults.get(field)) ;
            try {
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public T initializeEmpty() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
