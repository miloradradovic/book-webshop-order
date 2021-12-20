package com.example.orderservice.service;

import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import org.springframework.scheduling.annotation.Async;

public interface IOrderService {

    Order createOrder(Cart cart);
    void updateOrderStatus();

    Order findById(int orderId);
}
