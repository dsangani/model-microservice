package com.example.demo.controller;

import com.example.demo.dao.DataStore;
import com.example.demo.model.Person;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
public class PersonController {

    @GetMapping("/person/{name}")
    public String getPerson(@PathVariable(value="name") String name) {

        Person person = DataStore.getInstance().getPerson(name);

        if(person != null){
            String json = "{\n";
            json += "\"name\": " + JSONObject.quote(person.getName()) + ",\n";
            json += "\"about\": " + JSONObject.quote(person.getAbout()) + ",\n";
            json += "\"birthYear\": " + person.getBirthYear() + "\n";
            json += "}";
            return json;
        }
        else{
            //That person wasn't found, so return an empty JSON object. We could also return an error.
            return name;
        }

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

}
