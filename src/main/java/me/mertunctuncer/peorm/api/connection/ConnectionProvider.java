package me.mertunctuncer.peorm.api.connection;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();
}
