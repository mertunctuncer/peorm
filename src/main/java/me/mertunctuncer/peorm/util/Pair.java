package me.mertunctuncer.peorm.util;

import org.jetbrains.annotations.NotNull;

public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(@NotNull T first, @NotNull U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }
}
