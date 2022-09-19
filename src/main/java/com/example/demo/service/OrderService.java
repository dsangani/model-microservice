package com.example.demo.service;

import com.example.demo.dao.DbStore;
import com.example.demo.model.Item;
import com.example.demo.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class OrderService {

    PersonService personService;
    ItemService itemService;
    Connection connection;

    public String recordOrder(String personName, String itemName) throws SQLException {

        int personId = personService.getPersonIdByName(personName);
        int itemId = itemService.getItemIdByName(itemName);

        PreparedStatement st = connection.prepareStatement("INSERT INTO Orders (person_id, item_id, created_at) VALUES (?, ?, ?)");
        st.setInt(1, personId);
        st.setInt(2, itemId);
        st.setTimestamp(3, Timestamp.from(Instant.now()));
        st.executeUpdate();
        st.close();

        return "Order Saved";
    }

}
