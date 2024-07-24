package me.mertunctuncer.peorm.api.dao;

import me.mertunctuncer.peorm.api.query.Query;
import me.mertunctuncer.peorm.internal.model.Result;

public interface DatabaseExecutor {

    Result execute(Query query);
}
