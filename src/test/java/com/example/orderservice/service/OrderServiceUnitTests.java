package com.example.orderservice.service;

import com.example.orderservice.exception.CreateOrderFailException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.feign.BookFeign;
import com.example.orderservice.feign.UserFeign;
import com.example.orderservice.feign.client.BookCatalogData;
import com.example.orderservice.feign.client.CartClient;
import com.example.orderservice.feign.client.UserDataResponse;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.impl.OrderService;
import feign.FeignException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceUnitTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserFeign userFeign;

    @Mock
    private BookFeign bookFeign;

    @Test
    public void createSuccess() {
        Cart cart = ServiceTestUtils.generateCart("");
        CartClient cartClient = ServiceTestUtils.generateCartClient(cart);
        UserDataResponse userDataResponse = ServiceTestUtils.generateUserDataResponse();
        List<BookCatalogData> bookCatalogDataList = ServiceTestUtils.generateBookCatalogData();
        Order toCreate = ServiceTestUtils.generateOrder(cart, userDataResponse);
        Order created = ServiceTestUtils.generateCreatedOrder(toCreate);

        when(userFeign.getDataForOrder()).thenReturn(userDataResponse);
        when(bookFeign.getByCart(cartClient)).thenReturn(bookCatalogDataList);
        when(orderRepository.save(toCreate)).thenReturn(created);

        Order result = orderService.create(cart, cartClient, toCreate);
        verify(userFeign).getDataForOrder();
        verify(bookFeign).getByCart(cartClient);
        verify(orderRepository).save(toCreate);
        verifyNoMoreInteractions(userFeign);
        assertNotNull(result);
        assertEquals(created.getId(), result.getId());
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailUserEmail() {
        Cart cart = ServiceTestUtils.generateCart("useremail");
        CartClient cartClient = ServiceTestUtils.generateCartClient(cart);
        Order toCreate = ServiceTestUtils.generateOrder(cart, new UserDataResponse());

        when(userFeign.getDataForOrder()).thenThrow(FeignException.BadRequest.class);

        orderService.create(cart, cartClient, toCreate);
        verify(userFeign).getDataForOrder();
        verifyNoMoreInteractions(userFeign);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailUserService() {
        Cart cart = ServiceTestUtils.generateCart("userservice");
        CartClient cartClient = ServiceTestUtils.generateCartClient(cart);
        Order toCreate = ServiceTestUtils.generateOrder(cart, new UserDataResponse());

        when(userFeign.getDataForOrder()).thenThrow(FeignException.NotFound.class);

        orderService.create(cart, cartClient, toCreate);
        verify(userFeign).getDataForOrder();
        verifyNoMoreInteractions(userFeign);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailCart() {
        Cart cart = ServiceTestUtils.generateCart("cart");
        CartClient cartClient = ServiceTestUtils.generateCartClient(cart);
        UserDataResponse userDataResponse = ServiceTestUtils.generateUserDataResponse();
        Order toCreate = ServiceTestUtils.generateOrder(cart, userDataResponse);

        when(bookFeign.getByCart(cartClient)).thenThrow(FeignException.BadRequest.class);

        orderService.create(cart, cartClient, toCreate);
        verify(userFeign).getDataForOrder();
        verify(bookFeign).getByCart(cartClient);
        verifyNoMoreInteractions(userFeign, bookFeign);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailCatalogService() {
        Cart cart = ServiceTestUtils.generateCart("catalogservice");
        CartClient cartClient = ServiceTestUtils.generateCartClient(cart);
        UserDataResponse userDataResponse = ServiceTestUtils.generateUserDataResponse();
        Order toCreate = ServiceTestUtils.generateOrder(cart, userDataResponse);

        when(bookFeign.getByCart(cartClient)).thenThrow(FeignException.NotFound.class);

        orderService.create(cart, cartClient, toCreate);
        verify(userFeign).getDataForOrder();
        verify(bookFeign).getByCart(cartClient);
        verifyNoMoreInteractions(userFeign, bookFeign);
    }

    @Test
    public void updateStatusSuccess() {
        int listSize = ServiceTestUtils.generateOrderListSize();
        List<Order> orders = ServiceTestUtils.generateOrderList(listSize);
        List<Order> updated = ServiceTestUtils.generateUpdatedOrderList(orders);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderRepository.saveAll(orders)).thenReturn(updated);

        List<Order> result = orderService.updateStatus();
        verify(orderRepository).findAll();
        verify(orderRepository).saveAll(orders);
        verifyNoMoreInteractions(orderRepository);
        assertEquals(updated.get(0).getOrderStatus(), result.get(0).getOrderStatus());
    }

    @Test
    public void getByIdSuccess() {
        int orderId = ServiceTestUtils.generateOrderId(true);
        Order found = ServiceTestUtils.generateOrderFoundById(orderId);

        when(orderRepository.findById(orderId)).thenReturn(found);

        Order result = orderService.getById(orderId);
        verify(orderRepository).findById(orderId);
        verifyNoMoreInteractions(orderRepository);
        assertNotNull(result);
        assertEquals(orderId, result.getId());
    }

    @Test
    public void getByIdReturnsNull() {
        int orderId = ServiceTestUtils.generateOrderId(false);

        when(orderRepository.findById(orderId)).thenReturn(null);

        Order result = orderService.getById(orderId);
        verify(orderRepository).findById(orderId);
        verifyNoMoreInteractions(orderRepository);
        assertNull(result);
    }

    @Test
    public void getByIdThrowsExceptionSuccess() {
        int orderId = ServiceTestUtils.generateOrderId(true);
        Order found = ServiceTestUtils.generateOrderFoundById(orderId);

        when(orderRepository.findById(orderId)).thenReturn(found);

        Order result = orderService.getByIdThrowsException(orderId);
        verify(orderRepository).findById(orderId);
        verifyNoMoreInteractions(orderRepository);
        assertNotNull(result);
        assertEquals(orderId, result.getId());
    }

    @Test(expected = OrderNotFoundException.class)
    public void getByIdThrowsExceptionFail() {
        int orderId = ServiceTestUtils.generateOrderId(false);

        when(orderRepository.findById(orderId)).thenReturn(null);

        orderService.getByIdThrowsException(orderId);
        verify(orderRepository).findById(orderId);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void getAllSuccess() {
        int listSize = ServiceTestUtils.generateOrderListSize();
        List<Order> orders = ServiceTestUtils.generateOrderList(listSize);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAll();
        verify(orderRepository).findAll();
        verifyNoMoreInteractions(orderRepository);
        assertNotNull(result);
        assertEquals(listSize, result.size());
    }
}
