package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
}
