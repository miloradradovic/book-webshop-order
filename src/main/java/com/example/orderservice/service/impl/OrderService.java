package com.example.orderservice.service.impl;

import com.example.orderservice.exception.CreateOrderFailException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.feign.BookFeign;
import com.example.orderservice.feign.UserFeign;
import com.example.orderservice.feign.client.BookCatalogData;
import com.example.orderservice.feign.client.CartClient;
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
    public Order create(Cart cart, CartClient cartClient, Order toCreate) {
        try {
            if (cart.isDefaultInfo()) {
                UserDataResponse userDataResponse = userFeign.getDataForOrder();
                toCreate.setAddress(userDataResponse.getAddress());
                toCreate.setPhoneNumber(userDataResponse.getPhoneNumber());
            }
        } catch (Exception e) {
            throw new CreateOrderFailException();
        }

        try {
            List<BookCatalogData> bookCatalogData = bookFeign.getByCart(cartClient);
            for (BookCatalogData book : bookCatalogData) {
                OrderedItem orderedItem = new OrderedItem(book.getName(), cart.getCartItems().get(bookCatalogData.indexOf(book)).getAmount(), book.getPrice());
                toCreate.getOrderedItems().add(orderedItem);
            }
        } catch (Exception e) {
            throw new CreateOrderFailException();
        }

        Order saved = orderRepository.save(toCreate);
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
    public List<Order> updateStatus() {
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

        return orderRepository.saveAll(orders);
    }

    @Override
    public Order getById(int orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order getByIdThrowsException(int orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
