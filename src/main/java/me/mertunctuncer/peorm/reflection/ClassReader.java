package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.annotation.*;
import me.mertunctuncer.peorm.util.Pair;
import me.mertunctuncer.peorm.model.ColumnData;

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
        return mapFields(clazz, null);
    }

    public static <T> List<Pair<Field, ColumnData>> mapFields(Class<T> clazz, T defaultValueInstance) {

        Objects.requireNonNull(clazz, "Class must not be null");

        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    if(!field.trySetAccessible()) {
                        return null;
                    }

                    Column column = field.getAnnotation(Column.class);

                    Class<?> type = field.getType();
                    String name = column.name().isEmpty() ? field.getName() : column.name();
                    Integer size = column.size() == -1 ? null : column.size();
                    boolean nullable = column.nullable();

                    Object defaultValue = getDefaultValue(field, defaultValueInstance);

                    boolean primaryKey = field.isAnnotationPresent(PrimaryKey.class);
                    boolean foreignKey = field.isAnnotationPresent(ForeignKey.class);
                    boolean unique = field.isAnnotationPresent(Unique.class);
                    AutoIncrement autoIncrement = field.getAnnotation(AutoIncrement.class);

                    return new Pair<>(field, new ColumnData (
                            type,
                            name,
                            size,
                            defaultValue,
                            primaryKey,
                            foreignKey,
                            nullable,
                            unique,
                            autoIncrement
                    ));

                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Object getDefaultValue(Field field, Object valueProvider) {
        if(valueProvider == null) return null;

        try {
            return field.get(valueProvider);
        } catch (Exception e) {
            return null;
        }
    }
}


