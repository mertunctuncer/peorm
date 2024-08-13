package me.mertunctuncer.peorm.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SQLMap implements Collection<SQLPair> {


    private final Map<String, Integer> indexes;
    private final List<SQLPair> entries;

    public SQLMap(List<SQLPair> list) {
        this();
        addAll(list);
    }

    public SQLMap(Map<String, Object> values) {
        this();
        values.forEach((column, value) -> {
            entries.add(new SQLPair(column, value));
            indexes.put(column, indexes.size());
        });
    }

    public SQLMap() {
        this.indexes = new HashMap<>();
        this.entries = new ArrayList<>();

    }

    public Object get(int index) {
        return entries.get(index).getValue();
    }

    public Object get(String columnName) {
        return entries.get(indexes.get(columnName));
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return entries.contains(o);
    }

    @Override
    public @NotNull Iterator<SQLPair> iterator() {
        return entries.iterator();
    }

    @Override
    public void forEach(Consumer<? super SQLPair> action) {
        entries.forEach(action);
    }

    @Override
    public Object @NotNull [] toArray() {
        return entries.toArray();
    }

    @Override
    public <T> T @NotNull [] toArray(T @NotNull [] ts) {
        return entries.toArray(ts);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return entries.toArray(generator);
    }

    @Override
    public boolean add(SQLPair sqlPair) {
        indexes.put(sqlPair.getColumn(), indexes.size());
        return entries.add(sqlPair);
    }

    @Override
    public boolean remove(Object o) {

        boolean result = entries.remove(o);

        indexes.clear();

        for(int i = 0; i < entries.size(); i++) {
            indexes.put(entries.get(i).getColumn(), i);
        }

        return result;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return entries.contains(collection);
    }

    @Override
    public boolean addAll(Collection<? extends SQLPair> collection) {
        collection.forEach(this::add);
        return !collection.isEmpty();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        boolean result = entries.removeAll(collection);

        indexes.clear();

        for(int i = 0; i < entries.size(); i++) {
            indexes.put(entries.get(i).getColumn(), i);
        }

        return result;
    }

    @Override
    public boolean removeIf(Predicate<? super SQLPair> filter) {
        boolean result = entries.removeIf(filter);
        indexes.clear();
        for(int i = 0; i < entries.size(); i++) {
            indexes.put(entries.get(i).getColumn(), i);
        }
        return result;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        boolean result = entries.retainAll(collection);
        indexes.clear();
        for(int i = 0; i < entries.size(); i++) {
            indexes.put(entries.get(i).getColumn(), i);
        }
        return result;
    }

    @Override
    public void clear() {
        entries.clear();
        indexes.clear();
    }

    @Override
    public Spliterator<SQLPair> spliterator() {
        return entries.spliterator();
    }

    @Override
    public Stream<SQLPair> stream() {
        return entries.stream();
    }

    @Override
    public Stream<SQLPair> parallelStream() {
        return entries.parallelStream();
    }
}
