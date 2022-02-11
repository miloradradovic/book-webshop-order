package com.example.orderservice.exception;

import com.example.orderservice.exception.general.BadRequestException;

public class OrderNotFoundException extends BadRequestException {

    public OrderNotFoundException() {
        super("Order not found!");
    }
}
