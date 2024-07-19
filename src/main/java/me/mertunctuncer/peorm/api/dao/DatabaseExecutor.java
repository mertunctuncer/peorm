package me.mertunctuncer.peorm.api.dao;

import me.mertunctuncer.peorm.api.query.FetchQuery;
import me.mertunctuncer.peorm.api.query.Query;
import me.mertunctuncer.peorm.api.util.SQLDataPair;

import java.util.Set;

public interface DatabaseExecutor {

    boolean execute(Query query);

    Set<SQLDataPair> fetch(FetchQuery query);
}
