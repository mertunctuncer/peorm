package me.mertunctuncer.peorm.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SQLSet {
    private final Set<SQLDataPair> pairs = new HashSet<>();

    public SQLSet(SQLDataPair... pairs) {
        this.pairs.addAll(Arrays.asList(pairs));
    }

    public SQLSet(SQLDataPair pair) {
        this.pairs.add(pair);
    }

    public Set<SQLDataPair> getMappings() {
        return pairs;
    }

    public static SQLSet of(SQLDataPair... pairs) {
        return new SQLSet(pairs);
    }

    public static SQLSet of(SQLDataPair pair) {
        return new SQLSet(pair);
    }
}
