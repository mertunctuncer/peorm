package me.mertunctuncer.peorm.api.query;

import me.mertunctuncer.peorm.api.syntax.SyntaxProvider;
import me.mertunctuncer.peorm.internal.model.ColumnData;

import java.util.List;


public class CreateTableQuery implements Query {

    private final SyntaxProvider typeMapper;

    private String tableName;
    private boolean ifNotExists = false;
    private List<ColumnData> columns;

    public CreateTableQuery(SyntaxProvider typeMapper) {
        this.typeMapper = typeMapper;
    }

    public CreateTableQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CreateTableQuery setIfNotExists(boolean ifNotExists) {
        this.ifNotExists = ifNotExists;
        return this;
    }

    public CreateTableQuery setColumns(List<ColumnData> columns) {
        this.columns = columns;
        return this;
    }



    @Override
    public String asString() {
        // base query
        StringBuilder builder = new StringBuilder("CREATE TABLE ");

        if(ifNotExists) builder.append("IF NOT EXISTS ");
        builder.append(tableName);

        columns.forEach(columnData -> {
            // columns
            builder
                    .append(columnData.name())
                    .append(" ")
                    .append(typeMapper.getTypeSyntax(columnData.type()));

            if(columnData.size() != null) builder.append("(").append(columnData.size()).append(")");
            if(columnData.autoIncrement() != null)

            if(columnData.primaryKey()) builder.append(" PRIMARY KEY");
            if(columnData.foreignKey()) builder.append(" FOREIGN KEY");
            if(columnData.unique()) builder.append(" UNIQUE");
            if(!columnData.nullable()) builder.append(" NOT NULL");

            if(columnData.defaultValue() != null) builder.append(" DEFAULT ").append(columnData.defaultValue());


        });

        return builder.toString();
    }
}
