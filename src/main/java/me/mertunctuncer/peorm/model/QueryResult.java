package me.mertunctuncer.peorm.model;

import java.util.List;

public class QueryResult<T> {

    private final boolean success;
    private final List<T> result;
    private final Exception exception;

    public QueryResult(boolean success) {
        this.success = success;
        this.result = null;
        this.exception = null;
    }

    public QueryResult(Exception exception) {
        this.success = false;
        this.result = null;
        this.exception = exception;
    }

    public QueryResult(List<T> result) {
        this.success = true;
        this.result = result;
        this.exception = null;
    }


    public boolean isSuccessful() {
        return success;
    }

    public boolean hasResult() {
        return result != null && !result.isEmpty();
    }

    public List<T> getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }
}
