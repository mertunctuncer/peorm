package me.mertunctuncer.peorm.api.mapper;

public interface TypeMap {
    void setSyntax(Class<?> type, String syntax);
    String getSyntax(Class<?> type);
}
