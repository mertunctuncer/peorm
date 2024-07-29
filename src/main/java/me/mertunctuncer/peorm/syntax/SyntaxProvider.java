package me.mertunctuncer.peorm.syntax;

import me.mertunctuncer.peorm.query.*;

public interface SyntaxProvider {
    <T> String getSyntax(Query<T> query);

    String getTypeSyntax(Class<?> clazz);
}
