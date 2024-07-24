package me.mertunctuncer.peorm.db;

import java.util.List;

public class RawResult {

    private boolean success;
    private List<List<Object>> result;
    private Exception exception;


    public boolean isSuccessful() {
        return success;
    }

    public boolean hasResult() {
        return result != null && !result.isEmpty();
    }

    public List<List<Object>> getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }


    // Factory
    public static RawResult success() {
        RawResult rawResult = new RawResult();
        rawResult.success = true;
        return rawResult;
    }

    public static RawResult success(List<List<Object>> result) {
        RawResult rawResult = new RawResult();
        rawResult.success = true;
        rawResult.result = result;
        return rawResult;
    }

    public static RawResult fail(Exception exception) {
        RawResult rawResult = new RawResult();
        rawResult.success = false;
        rawResult.exception = exception;
        return rawResult;
    }


}
