package me.mertunctuncer.peorm.syntax;

import me.mertunctuncer.peorm.query.*;

public interface SyntaxProvider {

    <T> String getSyntax(CreateTableQuery<T> createTableQuery);
    <T> String getSyntax(DropTableQuery<T> dropTableQuery);
    <T> String getSyntax(InsertQuery<T> insertQuery);
    <T> String getSyntax(DeleteQuery<T> deleteQuery);
    <T> String getSyntax(SelectQuery<T> selectQuery);
    <T> String getSyntax(UpdateQuery<T> updateQuery);
    <T> String getSyntax(UpsertQuery<T> upsertQuery);

    String getTypeSyntax(Class<?> clazz);
}
