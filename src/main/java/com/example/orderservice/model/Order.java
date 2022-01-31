package com.example.orderservice.model;

import com.example.orderservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Set<OrderedItem> orderedItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "final_price", nullable = false)
    private double finalPrice;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public Order(Set<OrderedItem> orderedItems, String address, String phoneNumber) {
        this.orderedItems = orderedItems;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderStatus = OrderStatus.CREATED;
        this.finalPrice = calculateFinalPrice();
    }

    private double calculateFinalPrice() {
        double price = 0.0;
        for (OrderedItem item : orderedItems) {
            price += item.getPrice() * item.getAmount();
        }
        return price;
    }
}
