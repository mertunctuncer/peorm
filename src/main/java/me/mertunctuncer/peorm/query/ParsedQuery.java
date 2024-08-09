package me.mertunctuncer.peorm.query;

import java.util.List;

public interface ParsedQuery {

    String getStatement();
    List<Object> getParameters();
    Type getType();


    public enum Type {
        UPDATE,
        QUERY
    }
}
