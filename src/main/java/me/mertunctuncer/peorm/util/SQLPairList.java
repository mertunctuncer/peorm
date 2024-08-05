package me.mertunctuncer.peorm.util;

import me.mertunctuncer.peorm.model.ColumnProperties;
import me.mertunctuncer.peorm.model.ReflectionContainer;
import me.mertunctuncer.peorm.model.TableProperties;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class SQLPairList {
    private final Map<String, Object> data = new HashMap<>();
    private final List<String> index = new ArrayList<>();

    public Object getValueAt(int index) {
        return data.get(this.index.get(index));
    }

    public Object getValueOf(String column) {
        return data.get(column);
    }

    public Object getValueOfOrDefault(String column, Object defaultValue) {
        return data.getOrDefault(column, defaultValue);
    }

    public int size() {
        return index.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public List<SQLPair> asPairs() {
        return index.stream().map(column -> new SQLPair(column, data.get(column))).toList();
    }

    private Object put(String key, Object value) {
        index.add(key);
        return data.put(key, value);
    }

    public SQLPairList(SQLPair... pairs) {
        for(SQLPair pair : pairs) {
            index.add(pair.getColumn());
            data.put(pair.getColumn(), pair.getValue());
        }
    }

    public SQLPairList(SQLPair pair) {
        data.put(pair.getColumn(), pair.getValue());
        index.add(pair.getColumn());
    }

    public static final class Factory {

        public static <T> SQLPairList create(T data, TableProperties<T> tableProperties, ReflectionContainer<T> reflectionContainer, Predicate<ColumnProperties> columnFilter) {
            SQLPairList SQLPairList = new SQLPairList();

            for(ColumnProperties columnProperties : tableProperties.columns()) {
                if(!columnFilter.test(columnProperties)) continue;

                Field field = reflectionContainer.columnFieldMap().get(columnProperties.name());
                try {
                    SQLPairList.put(columnProperties.name(), field.get(data));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return SQLPairList;
        }
        public static <T> SQLPairList create(T data, TableProperties<T> tableProperties, ReflectionContainer<T> reflectionContainer) {
            SQLPairList SQLPairList = new SQLPairList();

            for(ColumnProperties columnProperties : tableProperties.columns()) {
                Field field = reflectionContainer.columnFieldMap().get(columnProperties.name());
                try {
                    SQLPairList.put(columnProperties.name(), field.get(data));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            return SQLPairList;
        }

        public static SQLPairList of(List<SQLPair> pairs) {
            SQLPairList SQLPairList = new SQLPairList();
            for(SQLPair pair : pairs) {
                SQLPairList.put(pair.getColumn(), pair.getValue());
            }
            return SQLPairList;
        }

        public static SQLPairList of(SQLPair pair) {
            return new SQLPairList(pair);
        }

        public static SQLPairList of(SQLPair... pairs) {
            return new SQLPairList(pairs);
        }
    }
}
