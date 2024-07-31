package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.annotation.*;
import me.mertunctuncer.peorm.model.ReflectionData;
import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.model.ColumnData;

import java.lang.reflect.Field;
import java.util.*;


public class ClassParser<T> {

    private final Class<T> clazz;
    private final Map<String, Field> fields = new HashMap<>();
    private final Map<Field, Object> defaults = new HashMap<>();


    public ClassParser(final Class<T> clazz) {
        this.clazz = Objects.requireNonNull(clazz);

        Arrays.stream(clazz.getDeclaredFields()).forEach(field ->  {
            field.setAccessible(true);

            Column column = field.getAnnotation(Column.class);
            String effectiveName = column != null && !column.name().isEmpty() ? column.name() : field.getName();

            fields.put(effectiveName, field);
        });
    }

    public ClassParser<T> setDefaults(T defaultProvider) {
        Objects.requireNonNull(defaultProvider);
        fields.values().forEach(field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(defaultProvider);
                defaults.put(field, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }

    public TableData<T> getTableData() {
        return new TableData<>(getTableName(), getColumnData());
    }
    public List<ColumnData> getColumnData() {
        return fields.values().stream().filter(field -> field.isAnnotationPresent(Column.class)).map(field -> {
            Column column = field.getAnnotation(Column.class);
            String effectiveName = column != null && !column.name().isEmpty() ? column.name() : field.getName();

            if (column == null) return null;

            Class<?> type = field.getType();
            Integer size = column.size() == -1 ? null : column.size();
            boolean nullable = column.nullable();

            Object defaultValue = defaults.get(field);

            boolean primaryKey = field.isAnnotationPresent(PrimaryKey.class);
            boolean foreignKey = field.isAnnotationPresent(ForeignKey.class);
            boolean unique = field.isAnnotationPresent(Unique.class);
            AutoIncrement autoIncrement = field.getAnnotation(AutoIncrement.class);

            return new ColumnData(
                    type,
                    effectiveName,
                    size,
                    defaultValue,
                    primaryKey,
                    foreignKey,
                    nullable,
                    unique,
                    autoIncrement
            );
        }).filter(Objects::nonNull).toList();
    }


    public String getTableName() {
        Table table = Objects.requireNonNull(clazz.getAnnotation(Table.class), "Class must be annotated with @Table");
        return table.name().isEmpty() ? clazz.getSimpleName().toLowerCase(Locale.ENGLISH) : table.name();
    }

    public ReflectionData<T> getReflectionData() {
        return new ReflectionData<>(clazz, fields);
    }

    public InstanceFactory<T> createInstanceFactory() {
        return new InstanceFactory<>(getReflectionData(), defaults);
    }
}


