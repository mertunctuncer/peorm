package me.mertunctuncer.peorm.db.syntax;

import me.mertunctuncer.peorm.db.query.Query;

public interface SyntaxProvider {
    <T> String getSyntax(Query<T> query);

    String getTypeSyntax(Class<?> clazz);
}
