package com.example.orderservice.feign.client;

import com.example.orderservice.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartClient {

    private List<Integer> bookIds;
}
