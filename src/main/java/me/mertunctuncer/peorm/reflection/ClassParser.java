package me.mertunctuncer.peorm.reflection;

import me.mertunctuncer.peorm.annotation.*;
import me.mertunctuncer.peorm.model.TableProperties;
import me.mertunctuncer.peorm.model.ColumnProperties;

import java.lang.reflect.Field;
import java.util.*;


public class ClassParser<T> {

    private final ReflectionContainer<T> reflectionContainer;
    private final List<Field> columnFields = new ArrayList<>();
    private final Map<String, String> fieldAliases = new HashMap<>();
    private final Map<Field, Object> defaults = new HashMap<>();


    public ClassParser(ReflectionContainer<T> reflectionContainer) {
        this.reflectionContainer = Objects.requireNonNull(reflectionContainer, "reflectionContainer must not be null");

        reflectionContainer.fields().entrySet().stream().filter(entry -> entry.getValue().isAnnotationPresent(Column.class)).forEach(
                entry -> {
                    columnFields.add(entry.getValue());
                    Column column = entry.getValue().getAnnotation(Column.class);
                    if(!column.name().isEmpty()) fieldAliases.put(entry.getKey(), column.name());
                }
        );
    }

    public Map<String, String> getFieldAliases() {
        return fieldAliases;
    }

    public void setDefaults(T defaultProvider) {
        Objects.requireNonNull(defaultProvider);
        columnFields.forEach(field -> {
            try {
                Object value = field.get(defaultProvider);
                defaults.put(field, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public TableProperties<T> getTableProperties() {
        return new TableProperties<>(getTableName(), getColumnProperties());
    }

    public List<ColumnProperties> getColumnProperties() {
        return columnFields.stream().map(field -> {
            Column column = field.getAnnotation(Column.class);
            String effectiveName = fieldAliases.getOrDefault(field.getName(), field.getName());

            Class<?> type = field.getType();
            Integer size = column.size() == -1 ? null : column.size();
            boolean nullable = column.nullable();

            Object defaultValue = defaults.get(field);

            boolean primaryKey = field.isAnnotationPresent(PrimaryKey.class);
            boolean foreignKey = field.isAnnotationPresent(ForeignKey.class);
            boolean unique = field.isAnnotationPresent(Unique.class);
            AutoIncrement autoIncrement = field.getAnnotation(AutoIncrement.class);

            return new ColumnProperties(
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
        }).toList();
    }

    public String getTableName() {
        Table table = Objects.requireNonNull(reflectionContainer.clazz().getAnnotation(Table.class), "Class must be annotated with @Table");
        return table.name().isEmpty() ? reflectionContainer.clazz().getSimpleName().toLowerCase(Locale.ENGLISH) : table.name();
    }
}


