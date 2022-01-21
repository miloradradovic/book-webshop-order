package com.example.orderservice.api;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.dto.CartItemDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderedItemDTO;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.CartItem;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderedItem;
import com.example.orderservice.model.enums.OrderStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApiTestUtils {

    public static CartDTO generateCartDTO(String success) {
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        CartItemDTO cartItemDTO = new CartItemDTO();
        switch (success) {
            case "":
                cartItemDTO.setAmount(1);
                cartItemDTO.setBookId(1);
                cartItemDTOList.add(cartItemDTO);
                cartDTO.setCartItems(cartItemDTOList);
                cartDTO.setAddress("Delivery address");
                cartDTO.setPhoneNumber("Delivery phone");
                cartDTO.setDefaultInfo(false);
                break;
            case "useremail":
            case "userservice":
                cartItemDTO.setAmount(1);
                cartItemDTO.setBookId(1);
                cartItemDTOList.add(cartItemDTO);
                cartDTO.setCartItems(cartItemDTOList);
                cartDTO.setAddress("Delivery address");
                cartDTO.setPhoneNumber("Delivery phone");
                cartDTO.setDefaultInfo(true);
                break;
            case "cart":
            case "catalogservice":
            case "editinstockid":  // edit in stock bad id
                cartItemDTO.setBookId(-1);
                cartItemDTO.setAmount(1);
                cartItemDTOList.add(cartItemDTO);
                cartDTO.setCartItems(cartItemDTOList);
                cartDTO.setAddress("Delivery address");
                cartDTO.setPhoneNumber("Delivery phone");
                cartDTO.setDefaultInfo(false);
                break;
            default:  // edit in stock bad amount
                cartItemDTO.setBookId(1);
                cartItemDTO.setAmount(1000);
                cartItemDTOList.add(cartItemDTO);
                cartDTO.setCartItems(cartItemDTOList);
                cartDTO.setAddress("Delivery address");
                cartDTO.setPhoneNumber("Delivery phone");
                cartDTO.setDefaultInfo(false);
                break;
        }
        return cartDTO;
    }

    public static Cart generateCart(CartDTO cartDTO) {
        CartItem cartItem = new CartItem(cartDTO.getCartItems().get(0).getBookId(), cartDTO.getCartItems().get(0).getAmount());
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        return new Cart(cartItems, cartDTO.isDefaultInfo(), cartDTO.getAddress(), cartDTO.getPhoneNumber());
    }

    public static Order generateCreatedOrder(Cart cart) {
        Set<OrderedItem> orderedItems = new HashSet<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderedItem orderedItem = new OrderedItem(2, "Bookk", cartItem.getAmount(), 30.0);
            orderedItems.add(orderedItem);
        }

        Order newOrder = new Order(orderedItems, cart.getAddress(), cart.getPhoneNumber());
        newOrder.setId(2);
        return newOrder;
    }

    public static OrderDTO generateOrderDTO(Order created) {
        List<OrderedItemDTO> orderedItemDTOList = new ArrayList<>();
        orderedItemDTOList.add(new OrderedItemDTO(2, "Bookk", 2, 30.0));
        return new OrderDTO(created.getId(), orderedItemDTOList, created.getOrderStatus().toString(), created.getAddress(),
                created.getPhoneNumber(), created.getFinalPrice());
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

    public static OrderDTO generateOrderDTOFoundById(int orderId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        return orderDTO;
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
            order.setAddress("Address");
            order.setOrderedItems(new HashSet<>());
            order.setPhoneNumber("Phone");
            order.setFinalPrice(40.0);
            orders.add(order);
        }
        return orders;
    }

    public static List<OrderDTO> generateOrderDTOList(List<Order> orders) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setOrderStatus(OrderStatus.SENT.toString());
            orderDTO.setAddress("Address");
            orderDTO.setOrderedItems(new ArrayList<>());
            orderDTO.setPhoneNumber("Phone");
            orderDTO.setFinalPrice(40.0);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
}
