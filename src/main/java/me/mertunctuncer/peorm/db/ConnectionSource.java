package me.mertunctuncer.peorm.db;

import java.sql.Connection;

public interface ConnectionSource {
    Connection getConnection();
}
