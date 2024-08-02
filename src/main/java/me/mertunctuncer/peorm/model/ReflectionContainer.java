package me.mertunctuncer.peorm.model;

import java.lang.reflect.Field;
import java.util.Map;

public record ReflectionContainer<T>(Class<T> clazz, Map<String, Field> columnFieldMap) {

}
