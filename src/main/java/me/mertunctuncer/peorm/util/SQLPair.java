package me.mertunctuncer.peorm.util;

public class SQLPair extends Pair<String, Object> {
    public SQLPair(String column, Object value) {
        super(column, value);
    }

    public String getColumn() {
        return first();
    }

    public Object getValue() {
        return second();
    }
}

