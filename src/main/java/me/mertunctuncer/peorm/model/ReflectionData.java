package me.mertunctuncer.peorm.model;

import java.lang.reflect.Field;
import java.util.Map;

public final class ReflectionData<T> {

    private final Class<T> clazz;
    private final Map<String, Field> columnFieldMap;

    public ReflectionData(final Class<T> clazz, final Map<String, Field> columnFieldMap) {
        this.clazz = clazz;
        this.columnFieldMap = columnFieldMap;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Map<String, Field> getColumnFieldMap() {
        return columnFieldMap;
    }
}
