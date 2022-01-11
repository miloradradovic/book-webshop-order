package com.example.orderservice.feign.client;

import com.example.orderservice.model.Cart;
import com.example.orderservice.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditInStock {

    private Map<Integer, Integer> amounts;

    public EditInStock(Cart cart) {
        amounts = new HashMap<>();
        for (CartItem item : cart.getCartItems()) {
            amounts.put(item.getBookId(), item.getAmount());
        }
    }
}
