package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private int id;
    private List<OrderedItemDTO> orderedItems;
    private String orderStatus;
    private String address;
    private String phoneNumber;
    private double finalPrice;

}
