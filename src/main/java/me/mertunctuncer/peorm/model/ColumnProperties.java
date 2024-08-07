package me.mertunctuncer.peorm.model;

import me.mertunctuncer.peorm.annotation.AutoIncrement;

public record ColumnProperties(
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
