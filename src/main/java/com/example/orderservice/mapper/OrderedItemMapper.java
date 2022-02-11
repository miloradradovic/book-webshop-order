package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderedItemDTO;
import com.example.orderservice.model.OrderedItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderedItemMapper {

    public OrderedItem toOrderedItem(OrderedItemDTO dto) {
        return new OrderedItem(dto.getName(), dto.getAmount(), dto.getPrice());
    }

    public OrderedItemDTO toOrderedItemDTO(OrderedItem orderedItem) {
        return new OrderedItemDTO(orderedItem.getId(), orderedItem.getName(), orderedItem.getAmount(), orderedItem.getPrice());
    }

    public Set<OrderedItem> toOrderedItemSet(List<OrderedItemDTO> dtos) {
        Set<OrderedItem> orderedItems = new HashSet<>();
        for (OrderedItemDTO dto : dtos) {
            orderedItems.add(toOrderedItem(dto));
        }
        return orderedItems;
    }

    public List<OrderedItemDTO> toOrderedItemDTOList(Set<OrderedItem> orderedItems) {
        List<OrderedItemDTO> dtos = new ArrayList<>();
        for (OrderedItem orderedItem : orderedItems) {
            dtos.add(toOrderedItemDTO(orderedItem));
        }
        return dtos;
    }
}
