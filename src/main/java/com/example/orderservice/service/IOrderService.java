package com.example.orderservice.service;

import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;

import java.util.List;

public interface IOrderService {

    Order create(Cart cart);
    List<Order> updateStatus();
    Order getById(int orderId);
    Order getByIdThrowsException(int orderId);
    List<Order> getAll();
}
