package com.orderserrvice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private String status; // pending , process, completed
    private String message;
    private Order order;
}
