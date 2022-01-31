package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    OrderedItemMapper orderedItemMapper;

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getId(), orderedItemMapper.toOrderedItemDTOList(order.getOrderedItems()),
                order.getOrderStatus().toString(), order.getAddress(), order.getPhoneNumber(), order.getFinalPrice());
    }

    public List<OrderDTO> toOrderDTOList(List<Order> orders) {
        List<OrderDTO> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(toOrderDTO(order));
        }
        return dtos;
    }

    public Order toOrder(Cart cart) {
        Order order = new Order();
        order.setOrderedItems(new HashSet<>());
        if (!cart.isDefaultInfo()) {
            order.setAddress(cart.getAddress());
            order.setPhoneNumber(cart.getPhoneNumber());
        }
        order.setFinalPrice(cart.getFinalPrice());
        order.setOrderStatus(OrderStatus.CREATED);
        return order;
    }
}
