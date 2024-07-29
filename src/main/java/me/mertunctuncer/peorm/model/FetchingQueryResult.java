package me.mertunctuncer.peorm.model;

import java.util.List;

public class FetchingQueryResult<T> extends QueryResult{

    private final List<T> resultList;

    public FetchingQueryResult(List<T> results) {
        super(true);
        this.resultList = results;
    }
    public FetchingQueryResult(Exception e) {
        super(e);
        this.resultList = null;
    }

    public List<T> getResults() {
        return resultList;
    }
}
