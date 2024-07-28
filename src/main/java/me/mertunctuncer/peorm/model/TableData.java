package me.mertunctuncer.peorm.model;

import java.util.List;

public record TableData<T>(
        String name,
        List<ColumnData> columns
) { }
