package com.example.demo.controller;

import com.example.demo.dao.DataStore;
import com.example.demo.dao.DbStore;
import com.example.demo.model.Person;
import com.example.demo.util.RateLimiter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    @GetMapping("/person/{name}")
    public String getPerson(@PathVariable(value="name") String name) throws JsonProcessingException {

        if (RateLimiter.getInstance().rateLimit(name)) {

            Person person = DataStore.getInstance().getPerson(name);

            if (person != null) {
                return new ObjectMapper().writeValueAsString(person);
            } else {
                //That person wasn't found, so return an empty JSON object. We could also return an error.
                return name;
            }
        }
        return "rate limited";
    }

    @PostMapping("/savePerson")
    public String setPerson(@RequestBody Person person)
    {
        // Storing the incoming data in the list
        DataStore.getInstance().putPerson(person);

        // Iterating using foreach loop
        for (Person p : DataStore.getInstance().personMap.values()) {
            System.out.println(p.getName() + " " + person.getBirthYear());
        }
        return "Data Inserted";
    }

    @PostMapping("/savePersonInDB")
    public String setPersonInDB(@RequestBody Person person) throws SQLException {
        // Storing the incoming data in the list

        PreparedStatement st = DbStore.getInstance().dbConnection.prepareStatement("INSERT INTO Person (name, about, birth_year) VALUES (?, ?, ?)");
        st.setString(1, person.getName());
        st.setString(2, person.getAbout());
        st.setInt(3, person.getBirthYear());
        st.executeUpdate();
        st.close();

        return "Data Inserted into db";
    }


    @GetMapping("/personFromDB/{name}")
    public String getPersonFromDB(@PathVariable(value="name") String name) throws SQLException, JsonProcessingException {
        // Storing the incoming data in the list

        PreparedStatement st = DbStore.getInstance().dbConnection.prepareStatement("SELECT * FROM Person where name = ?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();

        List<Person> result = new ArrayList<>();

        while (rs.next()) {
            Person person = new Person(rs.getString("name"), rs.getString("about"), rs.getInt("birth_year"));
            result.add(person);
        }
        return new ObjectMapper().writeValueAsString(result);
    }

}
