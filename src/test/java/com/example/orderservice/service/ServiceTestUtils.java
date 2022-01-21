package com.example.orderservice.service;

import com.example.orderservice.feign.client.BookCatalogData;
import com.example.orderservice.feign.client.CartClient;
import com.example.orderservice.feign.client.UserDataResponse;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.CartItem;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderedItem;
import com.example.orderservice.model.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;

import java.util.*;

public class ServiceTestUtils {

    public static Cart generateCart(String success) {
        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        switch (success) {
            case "":
                cartItem.setAmount(1);
                cartItem.setBookId(1);
                cartItemList.add(cartItem);
                cart.setCartItems(cartItemList);
                cart.setDefaultInfo(true);
                break;
            case "useremail":
            case "userservice":
                cartItem.setAmount(1);
                cartItem.setBookId(1);
                cartItemList.add(cartItem);
                cart.setCartItems(cartItemList);
                cart.setAddress("Delivery address");
                cart.setPhoneNumber("Delivery phone");
                cart.setDefaultInfo(true);
                break;
            case "cart":
            case "catalogservice":
            case "editinstockid":  // edit in stock bad id
                cartItem.setBookId(-1);
                cartItem.setAmount(1);
                cartItemList.add(cartItem);
                cart.setCartItems(cartItemList);
                cart.setAddress("Delivery address");
                cart.setPhoneNumber("Delivery phone");
                cart.setDefaultInfo(false);
                break;
            default:  // edit in stock bad amount
                cartItem.setBookId(1);
                cartItem.setAmount(1000);
                cartItemList.add(cartItem);
                cart.setCartItems(cartItemList);
                cart.setAddress("Delivery address");
                cart.setPhoneNumber("Delivery phone");
                cart.setDefaultInfo(false);
                break;
        }
        return cart;
    }

    public static UserDataResponse generateUserDataResponse() {
        return new UserDataResponse("Delivery address", "Delivery phone");
    }

    public static List<BookCatalogData> generateBookCatalogData() {
        List<BookCatalogData> bookCatalogDataList = new ArrayList<>();
        BookCatalogData bookCatalogData = new BookCatalogData(1, "Bookk", 30.0);
        bookCatalogDataList.add(bookCatalogData);
        return bookCatalogDataList;
    }

    public static Order generateOrder(Cart cart, UserDataResponse userDataResponse) {
        Order order = new Order();
        order.setOrderedItems(new HashSet<>());
        if (!cart.isDefaultInfo()) {
            order.setAddress(cart.getAddress());
            order.setPhoneNumber(cart.getPhoneNumber());
        } else {
            order.setAddress(userDataResponse.getAddress());
            order.setPhoneNumber(userDataResponse.getPhoneNumber());
        }
        return order;
    }

    public static Order generateCreatedOrder(Order toCreate) {
        return new Order(2, toCreate.getOrderedItems(), toCreate.getOrderStatus(), toCreate.getFinalPrice(), toCreate.getAddress(),
                toCreate.getPhoneNumber());
    }

    public static CartClient generateCartClient(Cart cart) {
        List<Integer> ids = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            ids.add(cartItem.getBookId());
        }
        return new CartClient(ids);
    }

    public static int generateOrderListSize() {
        return 1;
    }

    public static List<Order> generateOrderList(int listSize) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            Order order = new Order();
            order.setId(i + 1);
            order.setOrderStatus(OrderStatus.SENT);
            orders.add(order);
        }
        return orders;
    }

    public static List<Order> generateUpdatedOrderList(List<Order> orders) {
        List<Order> updated = new ArrayList<>();
        for (Order order : orders) {
            Order updatedOrder = new Order();
            if (order.getOrderStatus() == OrderStatus.CREATED) {
                String[] options = {"ACCEPTED", "NOT_ACCEPTED"};
                Random random = new Random();
                updatedOrder.setOrderStatus(OrderStatus.valueOf(options[random.nextInt(2)])); // sets it to accepted or not accepted randomly
            } else if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
                updatedOrder.setOrderStatus(OrderStatus.SENT);
            } else if (order.getOrderStatus() == OrderStatus.SENT) {
                updatedOrder.setOrderStatus(OrderStatus.DELIVERED);
            }
            updated.add(updatedOrder);
        }
        return updated;
    }

    public static int generateOrderId(boolean success) {
        if (success) {
            return 1;
        }
        return -1;
    }

    public static Order generateOrderFoundById(int orderId) {
        Order order = new Order();
        order.setId(orderId);
        return order;
    }
}
