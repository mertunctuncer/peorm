package me.mertunctuncer.peorm.db;

import java.sql.Connection;

public interface ConnectionSource extends AutoCloseable{
    Connection getConnection();
}
