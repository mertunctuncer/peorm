package me.mertunctuncer.peorm.api.syntax;

import me.mertunctuncer.peorm.internal.model.TableData;

public interface SyntaxProvider {
    String provideCreateTableQuery(TableData tableData, boolean ifNotExists);
    String provideDropTableQuery(TableData tableData, boolean ifNotExists);
}
