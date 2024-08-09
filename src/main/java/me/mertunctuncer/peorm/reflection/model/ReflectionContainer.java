package me.mertunctuncer.peorm.reflection.model;

import java.lang.reflect.Field;
import java.util.Map;

public record ReflectionContainer<T>(Class<T> clazz, Map<String, Field> fields) {

}
