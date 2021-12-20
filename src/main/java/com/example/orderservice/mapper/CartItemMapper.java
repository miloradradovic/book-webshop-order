package com.example.orderservice.mapper;

import com.example.orderservice.dto.CartItemDTO;
import com.example.orderservice.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemMapper {

    public CartItem toCartItem(CartItemDTO dto) {
        return new CartItem(dto.getBookId(), dto.getAmount());
    }

    public List<CartItem> toCartItemList(List<CartItemDTO> dtos) {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDTO dto : dtos) {
            cartItems.add(toCartItem(dto));
        }
        return cartItems;
    }
}
