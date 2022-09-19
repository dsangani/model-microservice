package com.example.demo.controller;

import com.example.demo.dao.DbStore;
import com.example.demo.model.Orders;
import com.example.demo.service.ItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PersonService;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class OrderController {

    private PersonService personService = new PersonService(DbStore.getInstance().dbConnection);
    private ItemService itemService = new ItemService(DbStore.getInstance().dbConnection);
    private OrderService orderService = new OrderService(personService, itemService, DbStore.getInstance().dbConnection);

    @PostMapping("/placeOrder")
    public String orderItem(@RequestBody Orders order) throws SQLException {

        return orderService.recordOrder(order.getPerson(), order.getItem());

    }

}
