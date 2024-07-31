package me.mertunctuncer.peorm.util;

import java.util.*;

public class IndexedSQLMap {
    private final Map<String, Object> data = new HashMap<>();
    private final List<String> index = new ArrayList<>();

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

    public Object getValueAt(int index) {
        return data.get(this.index.get(index));
    }

    public Object getValue(String column) {
        return data.get(column);
    }

    public Object put(String key, Object value) {
        index.add(key);
        return data.put(key, value);
    }

    public Object remove(String column) {
        index.remove(column);
        return data.remove(column);
    }

    public void putAll(Map<? extends String, ?> m) {
        data.putAll(m);
        index.addAll(m.keySet());
    }

    public void clear() {
        data.clear();
        index.clear();
    }

    public int size() {
        return index.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean containsColumn(String column) {
        return data.containsKey(column);
    }

    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    public List<String> columnList() {
        return Collections.unmodifiableList(index);
    }

    public List<Object> values() {
        return index.stream().map(data::get).toList();
    }

    public List<SQLPair> getEntries() {
        return index.stream().map(column -> new SQLPair(column, data.get(column))).toList();
    }
}
