package me.mertunctuncer.peorm.db;

public class QueryResult {

    private final boolean success;
    private final Exception exception;

    public QueryResult(boolean success) {
        this.success = success;
        this.exception = null;
    }

    public QueryResult(Exception exception) {
        this.success = false;
        this.exception = exception;
    }

    public boolean isSuccessful() {
        return success;
    }

    public Exception getException() {
        return exception;
    }
}
