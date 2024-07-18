package me.mertunctuncer.peorm.internal.reflection.model;

import java.util.List;

public record TableData(String name, List<ColumnData> columns) { }
