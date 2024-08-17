package com.orderserrvice.controller;

import com.orderserrvice.dto.OrderEvent;
import com.orderserrvice.producer.OrderPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service/orders")
public class OrderController {

    @Autowired
    private OrderPublisher orderPublisher;

    @PostMapping()
    public boolean sendOrderDetails(@RequestBody OrderEvent orderEvent){
        return orderPublisher.publicOrderMessage(orderEvent);
    }

    @PostMapping("/email")
    public boolean sendOrderEmail(@RequestBody OrderEvent orderEvent){
        return orderPublisher.publishToEmailQueue(orderEvent);
    }
}
