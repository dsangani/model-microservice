package com.example.demo.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Orders {

    private Person person;
    private Item item;
    private Date date;

}
