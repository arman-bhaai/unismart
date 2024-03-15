package com.appvillage.unismart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/test2";
        String user = "root";
        String pass = "";
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
}
