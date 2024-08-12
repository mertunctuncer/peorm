package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.util.SQLPair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InstanceFactory<T> {


    private final Class<T> clazz;
    private final Map<String, Field> fields;

    public InstanceFactory(ReflectionContainer<T> reflectionContainer, Map<String, String> fieldAliases) {
        this.clazz = reflectionContainer.clazz();
        this.fields = reflectionContainer.fields();
        fieldAliases.forEach((alias, fieldName) -> fields.put(alias, fields.get(fieldName)));
    }

    public T createWithOverrides(Set<SQLPair> overrides) {
        Objects.requireNonNull(overrides, "Overrides must not be null");

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            overrides.forEach((pair) -> {
                try {
                    fields.get(pair.getColumn()).set(instance, pair.getValue());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });

            return instance;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public T create() {
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
