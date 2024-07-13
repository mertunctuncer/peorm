package me.mertunctuncer.peorm.internal.reflection.model;

import me.mertunctuncer.peorm.api.annotation.Table;

public record ClassData <T>(
        Table table,
        Class<T> clazz,
        FieldData[] fields
) {
}
