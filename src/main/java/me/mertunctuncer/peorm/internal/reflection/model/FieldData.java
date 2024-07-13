package me.mertunctuncer.peorm.internal.reflection.model;

import java.lang.reflect.Field;

public record FieldData(
        Class<?> type,
        Field field,
        String name,
        AnnotationData data
){ }
