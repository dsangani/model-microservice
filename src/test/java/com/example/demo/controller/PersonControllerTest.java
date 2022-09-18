package com.example.demo.controller;

import com.example.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPersonNameIfNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/person/dhawal").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("dhawal")));
    }

    @Test
    public void getPersonNameIfFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/person/Dhawal").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\n\"name\": \"Dhawal\",\n\"about\": \"Dhawal needs a raise\",\n\"birthYear\": 1987\n}")));
    }

    @Test
    public void postNewPerson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/savePerson").contentType(MediaType.APPLICATION_JSON).content(toJson(new Person("Alana", "confident", 1987))))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Data Inserted")));
    }

    private String toJson(Person person) {
        try {
            return new ObjectMapper().writeValueAsString(person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}