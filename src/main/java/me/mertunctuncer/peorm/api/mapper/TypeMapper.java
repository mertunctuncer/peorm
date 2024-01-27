package me.mertunctuncer.peorm.api.mapper;

import me.mertunctuncer.peorm.api.mapper.ColumnType;

public interface TypeMapper {

    ColumnType getColumnType(Class<?> clazz);
}
