package me.mertunctuncer.peorm.util;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class IndexedSQLMap {
    private final Map<String, Object> data = new HashMap<>();
    private final List<String> index = new ArrayList<>();

    public Object getValueAt(int index) {
        return data.get(this.index.get(index));
    }

    public Object getValue(String column) {
        return data.get(column);
    }

    public Object getValueOrDefault(String column, Object defaultValue) {
        return data.getOrDefault(column, defaultValue);
    }

    public int size() {
        return index.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public List<SQLPair> getEntries() {
        return index.stream().map(column -> new SQLPair(column, data.get(column))).toList();
    }

    private Object put(String key, Object value) {
        index.add(key);
        return data.put(key, value);
    }

    public IndexedSQLMap(SQLPair... pairs) {
        for(SQLPair pair : pairs) {
            index.add(pair.getColumn());
            data.put(pair.getColumn(), pair.getValue());
        }
    }

    public IndexedSQLMap(SQLPair pair) {
        data.put(pair.getColumn(), pair.getValue());
        index.add(pair.getColumn());
    }

    public static final class Factory {

        public static <T> IndexedSQLMap create(T data, TableProperties<T> tableProperties, ReflectionContainer<T> reflectionContainer, Predicate<ColumnProperties> columnFilter) {
            IndexedSQLMap indexedSQLMap = new IndexedSQLMap();

            for(ColumnProperties columnProperties : tableProperties.columns()) {
                if(!columnFilter.test(columnProperties)) continue;

                Field field = reflectionContainer.columnFieldMap().get(columnProperties.name());
                try {
                    indexedSQLMap.put(columnProperties.name(), field.get(data));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return indexedSQLMap;
        }
        public static <T> IndexedSQLMap create(T data, TableProperties<T> tableProperties, ReflectionContainer<T> reflectionContainer) {
            IndexedSQLMap indexedSQLMap = new IndexedSQLMap();

            for(ColumnProperties columnProperties : tableProperties.columns()) {
                Field field = reflectionContainer.columnFieldMap().get(columnProperties.name());
                try {
                    indexedSQLMap.put(columnProperties.name(), field.get(data));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            return indexedSQLMap;
        }

        public static IndexedSQLMap of(List<SQLPair> pairs) {
            IndexedSQLMap indexedSQLMap = new IndexedSQLMap();
            for(SQLPair pair : pairs) {
                indexedSQLMap.put(pair.getColumn(), pair.getValue());
            }
            return indexedSQLMap;
        }

        public static IndexedSQLMap of(SQLPair pair) {
            return new IndexedSQLMap(pair);
        }

        public static IndexedSQLMap of(SQLPair... pairs) {
            return new IndexedSQLMap(pairs);
        }
    }
}
