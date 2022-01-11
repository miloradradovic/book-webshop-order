package com.example.orderservice.model;

import com.example.orderservice.feign.client.CartClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {

    private List<CartItem> cartItems;
    private boolean defaultInfo; // if true then use address and phone number of currently logged-in user
    private String address;
    private String phoneNumber;

    public Cart(List<CartItem> toCartItemList) {
        this.cartItems = toCartItemList;
        this.defaultInfo = true;
    }

    public Cart(List<CartItem> toCartItemList, String address, String phoneNumber) {
        this.cartItems = toCartItemList;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.defaultInfo = false;
    }

    public CartClient toCartClient() {
        List<Integer> ids = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            ids.add(cartItem.getBookId());
        }
        return new CartClient(ids);
    }
}
