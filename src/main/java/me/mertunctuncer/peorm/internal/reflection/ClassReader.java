package me.mertunctuncer.peorm.internal.reflection;

import me.mertunctuncer.peorm.api.annotation.*;
import me.mertunctuncer.peorm.api.util.Pair;
import me.mertunctuncer.peorm.internal.model.ColumnData;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public final class ClassReader {

    public static String getTableName(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Table table = Objects.requireNonNull(clazz.getAnnotation(Table.class), "Class must be annotated with @Table");
        return table.name().isEmpty() ? clazz.getSimpleName().toLowerCase(Locale.ENGLISH) : table.name();
    }

    public static List<Pair<Field, ColumnData>> mapFields(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class must not be null");

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> new Pair<>(field, new ColumnData(
                        getType(field),
                        getEffectiveName(field),
                        getSize(field),
                        isPrimaryKey(field),
                        isForeignKey(field),
                        isNullable(field),
                        isUnique(field),
                        isIdentity(field),
                        getIdentitySeed(field),
                        getIdentityAmount(field),
                        isAutoIncrement(field),
                        getAutoIncrementAMount(field)
                ))).collect(Collectors.toList());
    }

    private static Class<?> getType(Field field) {
        return field.getType();
    }

    private static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(PrimaryKey.class);
    }

    private static boolean isForeignKey(Field field) {
        return field.isAnnotationPresent(ForeignKey.class);
    }

    private static boolean isNullable(Field field) {
        return !field.isAnnotationPresent(NotNull.class);
    }

    private static boolean isUnique(Field field) {
        return field.isAnnotationPresent(Unique.class);
    }

    private static String getEffectiveName(Field field) {
        Column column = Objects.requireNonNull(field.getAnnotation(Column.class));
        return column.name().isEmpty() ? field.getName() : column.name();
    }

    private static short getSize(Field field) {
        Size annotation = field.getAnnotation(Size.class);
        return annotation == null ? -1 : annotation.size();
    }

    private static boolean isIdentity(Field field) {
        return field.isAnnotationPresent(Identity.class);
    }

    private static long getIdentitySeed(Field field) {
        Identity identity = field.getAnnotation(Identity.class);
        return identity == null ? 0 : identity.seed();
    }

    private static int getIdentityAmount(Field field) {
        Identity identity = field.getAnnotation(Identity.class);
        return identity == null ? 0 : identity.increment();
    }

    private static boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(AutoIncrement.class);
    }

    private static int getAutoIncrementAMount(Field field) {
        AutoIncrement autoIncrement = field.getAnnotation(AutoIncrement.class);
        return autoIncrement == null ? 0 : autoIncrement.increment();
    }
}


