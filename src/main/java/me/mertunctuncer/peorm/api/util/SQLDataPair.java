package me.mertunctuncer.peorm.api.util;

public class SQLDataPair extends Pair<String, Object>{
    public SQLDataPair(String column, Object value) {
        super(column, value);
    }

    public String getColumn() {
        return first();
    }

    public Object getValue() {
        return second();
    }
}

