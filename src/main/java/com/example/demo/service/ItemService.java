package com.example.demo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemService {

    Connection dbConnection;

    ItemService(Connection connection) {
        dbConnection = connection;
    }

    public int getItemIdByName(String name) throws SQLException {

        int id = 0;
        PreparedStatement st = dbConnection.prepareStatement("SELECT id FROM Item where name = ?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        rs.next();
        id = rs.getInt(1);

        return id;
        
    }

}
