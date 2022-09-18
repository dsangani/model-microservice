package com.example.demo.model;

import lombok.Getter;

@Getter
public class Person {
    private String name;
    private String about;
    private int birthYear;

    public Person(String name, String about, int birthYear) {
        this.name = name;
        this.about = about;
        this.birthYear = birthYear;
    }
}