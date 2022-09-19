package com.example.demo.service;

import com.example.demo.dao.DbStore;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonService {

    Connection dbConnection;

    PersonService(Connection connection) {
        dbConnection = connection;
    }

    public int getPersonIdByName(String name) throws SQLException {

        int id = 0;
        PreparedStatement st = dbConnection.prepareStatement("SELECT id FROM Person where name = ?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        rs.next();
        id = rs.getInt(1);

        return id;

    }

}
