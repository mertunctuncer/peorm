package me.mertunctuncer.peorm.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

public class InstanceFactory<T> {

    private final Class<T> clazz;
    private final Map<String, Field> fields;
    private final Map<Field, Object> defaults;

    public InstanceFactory(Class<T> clazz, Map<String, Field> fields, Map<Field, Object> defaults) {
        this.clazz = clazz;
        this.fields = fields;
        this.defaults = defaults;
    }

    public T initializeDefault() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T instance = initializeEmpty();

        for(Map.Entry<String, Field> entry : fields.entrySet()) {
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

        for(Map.Entry<String, Field> entry : fields.entrySet()) {
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
