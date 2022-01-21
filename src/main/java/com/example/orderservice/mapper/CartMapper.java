package com.example.orderservice.mapper;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.feign.client.CartClient;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public CartClient toCartClient(Cart cart) {
        List<Integer> ids = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            ids.add(cartItem.getBookId());
        }
        return new CartClient(ids);
    }
}
