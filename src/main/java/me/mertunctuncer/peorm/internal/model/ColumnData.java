package me.mertunctuncer.peorm.internal.model;

import me.mertunctuncer.peorm.api.annotation.AutoIncrement;

public record ColumnData (
        Class<?> type,
        String name,
        Integer size,
        Object defaultValue,
        boolean primaryKey,
        boolean foreignKey,
        boolean nullable,
        boolean unique,
        AutoIncrement autoIncrement
) {
}
