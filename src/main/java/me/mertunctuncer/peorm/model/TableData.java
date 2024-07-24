package me.mertunctuncer.peorm.model;

import java.util.List;

public record TableData(String name, List<ColumnData> columns) { }
