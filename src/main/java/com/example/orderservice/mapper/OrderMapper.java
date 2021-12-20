package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    OrderedItemMapper orderedItemMapper;

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getId(), orderedItemMapper.toOrderedItemDTOList(order.getOrderedItems()),
                order.getOrderStatus().toString(), order.getAddress(), order.getPhoneNumber(), order.getFinalPrice());
    }
}
