package me.mertunctuncer.peorm.model;

import java.util.List;

public record TableProperties<T>(
        String name,
        List<ColumnProperties> columns
) { }
