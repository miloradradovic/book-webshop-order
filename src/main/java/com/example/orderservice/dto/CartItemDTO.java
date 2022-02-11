package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDTO {

    @NotNull(message = "Book id must be provided!")
    private int bookId;

    @NotNull(message = "Amount must be provided!")
    private int amount;
}
