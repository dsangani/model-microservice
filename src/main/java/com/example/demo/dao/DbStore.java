package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Example DataStore class that provides access to user data.
 * Pretend this class accesses a database.
 */
public class DbStore {

    private final String url = "jdbc:postgresql://localhost/dsangani";
    private final String user = "postgres";
    private final String password = "";

    public Connection dbConnection;

    private static DbStore instance = new DbStore();

    public static DbStore getInstance(){
        return instance;
    }

    private DbStore() {
        dbConnection = connect();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}