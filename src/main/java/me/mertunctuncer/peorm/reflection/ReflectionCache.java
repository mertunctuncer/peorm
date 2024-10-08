package me.mertunctuncer.peorm.reflection;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


public class ReflectionCache {
    private final Map<Class<?>, List<Field>> fields = new ConcurrentHashMap<>();
    private final Function<Field, Field> beforeCache;

    public ReflectionCache(Function<Field, Field> onFieldCache) {
        this.beforeCache = onFieldCache;
    }

    public List<Field> getFields(Class<?> clazz) {
        List<Field> fields = this.fields.get(clazz);
        if(fields == null) return Collections.emptyList();

        return Collections.unmodifiableList(fields);
    }

    public void cache(Class<?> clazz) {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .map(beforeCache)
                .toList();

        this.fields.put(clazz, fields);
    }

    public void remove(Class<?> clazz) {
        fields.remove(clazz);
    }

    public <T> ReflectionContainer<T> getAsContainer(Class<T> clazz) {

        if(!fields.containsKey(clazz)) cache(clazz);

        Map<String, Field> fieldMap = new HashMap<>();

        for(Field field: fields.getOrDefault(clazz, Collections.emptyList())) {
            fieldMap.put(field.getName(), field);
        }
        return new ReflectionContainer<>(clazz, fieldMap);
    }
}
