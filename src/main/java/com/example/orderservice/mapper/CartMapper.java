package com.example.orderservice.mapper;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    @Autowired
    CartItemMapper cartItemMapper;

    public Cart toCart(CartDTO dto) {
        if (dto.isDefaultInfo()) {
            return new Cart(cartItemMapper.toCartItemList(dto.getCartItems()));
        }
        return new Cart(cartItemMapper.toCartItemList(dto.getCartItems()), dto.getAddress(), dto.getPhoneNumber());
    }
}
