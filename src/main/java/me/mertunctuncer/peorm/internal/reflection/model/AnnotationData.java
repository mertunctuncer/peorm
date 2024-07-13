package me.mertunctuncer.peorm.internal.reflection.model;

import me.mertunctuncer.peorm.api.annotation.*;

public record AnnotationData(
        Column column,
        Size size,
        Unique unique,
        NotNull notNull,
        PrimaryKey primaryKey,
        ForeignKey foreignKey,
        Identity identity,
        AutoIncrement autoIncrement
) {
}
