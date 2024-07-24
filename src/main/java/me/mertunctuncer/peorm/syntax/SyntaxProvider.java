package me.mertunctuncer.peorm.syntax;

import me.mertunctuncer.peorm.model.TableData;
import me.mertunctuncer.peorm.query.Query;

public interface SyntaxProvider {
    String getSyntax(Query query);
    String provideCreateTableQuery(TableData tableData, boolean ifNotExists);
    String provideDropTableQuery(TableData tableData, boolean ifNotExists);
}
