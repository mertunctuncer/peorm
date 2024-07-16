package me.mertunctuncer.peorm.internal.reflection.model;

public record ColumnData(
        Class<?> type,
        String name,
        short size,
        boolean primaryKey,
        boolean foreignKey,
        boolean nullable,
        boolean unique,
        boolean identity,
        long identitySeed,
        int identityIncrementAmount,
        boolean autoIncrement,
        int autoIncrementAmount
) {
}
