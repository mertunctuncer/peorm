package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InstanceFactory<T> {


    private final Class<T> clazz;
    private final Map<String, Field> fields;

    public InstanceFactory(ReflectionContainer<T> reflectionContainer) {
        this.clazz = reflectionContainer.clazz();
        this.fields = reflectionContainer.fields();
    }

    public void addAlias(Map<String, String> aliases) {
        aliases.forEach(this::addAlias);

    }
    public void addAlias(String alias, String fieldName) {
        fields.put(alias, fields.get(fieldName));
    }

    public T createWithFieldValues(Set<Pair<? extends String, Object>> fieldValues) {
        Objects.requireNonNull(fieldValues, "Overrides must not be null");

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            fieldValues.forEach((pair) -> {
                try {
                    fields.get(pair.first()).set(instance, pair.second());
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
