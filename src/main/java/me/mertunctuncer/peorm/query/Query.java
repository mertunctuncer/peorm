package me.mertunctuncer.peorm.query;

import java.util.List;

public interface Query {

    List<Object> getParameters();
    boolean isFetching();
    String asString();
}
