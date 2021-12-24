package com.example.orderservice.service.impl;

import com.example.orderservice.exception.CreateOrderFailException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.feign.BookFeign;
import com.example.orderservice.feign.UserFeign;
import com.example.orderservice.feign.client.BookCatalogData;
import com.example.orderservice.feign.client.EditInStock;
import com.example.orderservice.feign.client.UserDataResponse;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderedItem;
import com.example.orderservice.model.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserFeign userFeign;

    @Autowired
    BookFeign bookFeign;

    @Override
    public Order createOrder(Cart cart) {
        try {
            if (cart.isDefaultInfo()) {
                UserDataResponse userDataResponse = userFeign.getUserData();
                cart.setAddress(userDataResponse.getAddress());
                cart.setPhoneNumber(userDataResponse.getPhoneNumber());
            }
        } catch (Exception e) {
            throw new CreateOrderFailException();
        }

        Set<OrderedItem> orderedItems = new HashSet<>();
        try {
            List<BookCatalogData> bookCatalogData = bookFeign.getBookData(cart.toCartClient());
            for (BookCatalogData book : bookCatalogData) {
                OrderedItem orderedItem = new OrderedItem(book.getName(), cart.getCartItems().get(bookCatalogData.indexOf(book)).getAmount(), book.getPrice());
                orderedItems.add(orderedItem);
            }
        } catch (Exception e) {
            throw new CreateOrderFailException();
        }

        Order saved = orderRepository.save(new Order(orderedItems, cart.getAddress(), cart.getPhoneNumber()));
        try {
            bookFeign.editInStock(new EditInStock(cart));
            return saved;
        } catch (Exception e) {
            orderRepository.delete(saved);
            throw new CreateOrderFailException();
        }
    }

    @Override
    @Scheduled(fixedRate = 20000, initialDelay = 20000)
    @Async
    public void updateOrderStatus() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if (order.getOrderStatus() == OrderStatus.CREATED) {
                String[] options = {"ACCEPTED", "NOT_ACCEPTED"};
                Random random = new Random();
                order.setOrderStatus(OrderStatus.valueOf(options[random.nextInt(2)])); // sets it to accepted or not accepted randomly
            } else if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
                order.setOrderStatus(OrderStatus.SENT);
            } else if (order.getOrderStatus() == OrderStatus.SENT) {
                order.setOrderStatus(OrderStatus.DELIVERED);
            }
        }

        orderRepository.saveAll(orders);
    }

    @Override
    public Order findById(int orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return order;
    }
}
