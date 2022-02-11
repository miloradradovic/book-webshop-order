package com.example.orderservice.exception;

import com.example.orderservice.exception.general.BadRequestException;

public class CreateOrderFailException extends BadRequestException {

    public CreateOrderFailException() {
        super("Failed to create order!");
    }
}
