package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.model.ReflectionData;

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

    public T initializeDefault() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T instance = initializeEmpty();

        for(Map.Entry<String, Field> entry : columnFieldMap.entrySet()) {
            Field field = entry.getValue();
            Object value = defaults.get(field);
            field.set(instance, value);
        }
        return instance;
    }

    public T initialize(Map<String, Object> fieldValueOverrides)
            throws IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException
    {
        Objects.requireNonNull(fieldValueOverrides, "Overrides must not be null");

        T instance = initializeEmpty();

        for(Map.Entry<String, Field> entry : columnFieldMap.entrySet()) {
            Field field = entry.getValue();
            Object value = fieldValueOverrides.getOrDefault(entry.getKey(), defaults.get(field));
            field.set(instance, value);
        }

        return instance;
    }
    public T initializeEmpty() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clazz.getDeclaredConstructor().newInstance();
    }
}
