package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDTO {

    @Size(min = 1, message = "Please provide at least one cart item!")
    private List<CartItemDTO> cartItems;

    @NotNull(message = "Default info must be provided!")
    private boolean defaultInfo; // if true then use address and phone number of currently logged-in user
    private String address;
    private String phoneNumber;

    @NotNull(message = "Final price must be provided!")
    private double finalPrice;
}
