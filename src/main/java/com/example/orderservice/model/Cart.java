package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double finalPrice;

    public Cart(List<CartItem> toCartItemList, double finalPrice) {
        this.cartItems = toCartItemList;
        this.defaultInfo = true;
        this.finalPrice = finalPrice;
    }

    public Cart(List<CartItem> toCartItemList, String address, String phoneNumber, double finalPrice) {
        this.cartItems = toCartItemList;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.defaultInfo = false;
        this.finalPrice = finalPrice;
    }
}
