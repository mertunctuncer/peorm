package me.mertunctuncer.peorm.query;

import me.mertunctuncer.peorm.model.TableData;

import java.lang.reflect.Field;
import java.util.Map;

public record InsertQuery<T>(T data, Map<String, Field> fields, TableData<T> tableData) implements Query<T> { }
