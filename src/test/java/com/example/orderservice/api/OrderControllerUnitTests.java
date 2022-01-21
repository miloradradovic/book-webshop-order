package com.example.orderservice.api;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.exception.CreateOrderFailException;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.feign.client.CartClient;
import com.example.orderservice.mapper.CartMapper;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.impl.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerUnitTests {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderService orderService;

    @Mock
    private CartMapper cartMapper;

    @Test
    public void createSuccess() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);
        Order created = ApiTestUtils.generateCreatedOrder(cart);
        OrderDTO orderDTO = ApiTestUtils.generateOrderDTO(created);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenReturn(created);
        when(orderMapper.toOrderDTO(created)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verify(orderMapper).toOrderDTO(created);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(created.getId(), response.getBody().getId());
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailUserEmail() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("useremail");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailUserService() { // user service not active
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("userservice");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailCart() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("cart");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailCatalogService() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("catalogservice");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailEditInStockId() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("editinstockid");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test(expected = CreateOrderFailException.class)
    public void createFailEditInStockAmount() {
        CartDTO cartDTO = ApiTestUtils.generateCartDTO("editinstockamount");
        Cart cart = ApiTestUtils.generateCart(cartDTO);
        CartClient cartClient = ApiTestUtils.generateCartClient(cart);
        Order toCreate = ApiTestUtils.generateOrderToCreate(cart);

        when(cartMapper.toCart(cartDTO)).thenReturn(cart);
        when(cartMapper.toCartClient(cart)).thenReturn(cartClient);
        when(orderMapper.toOrder(cart)).thenReturn(toCreate);
        when(orderService.create(cart, cartClient, toCreate)).thenThrow(CreateOrderFailException.class);

        orderController.create(cartDTO);
        verify(cartMapper).toCart(cartDTO);
        verify(cartMapper).toCartClient(cart);
        verify(orderMapper).toOrder(cart);
        verify(orderService).create(cart, cartClient, toCreate);
        verifyNoMoreInteractions(cartMapper, orderService, orderMapper);
    }

    @Test
    public void getByIdSuccess() {
        int orderId = ApiTestUtils.generateOrderId(true);
        Order found = ApiTestUtils.generateOrderFoundById(orderId);
        OrderDTO orderDTO = ApiTestUtils.generateOrderDTOFoundById(orderId);

        when(orderService.getByIdThrowsException(orderId)).thenReturn(found);
        when(orderMapper.toOrderDTO(found)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.getById(orderId);
        verify(orderService).getByIdThrowsException(orderId);
        verify(orderMapper).toOrderDTO(found);
        verifyNoMoreInteractions(orderService, orderMapper);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(found.getId(), response.getBody().getId());
    }

    @Test(expected = OrderNotFoundException.class)
    public void getByIdFail() {
        int orderId = ApiTestUtils.generateOrderId(false);

        when(orderService.getByIdThrowsException(orderId)).thenThrow(OrderNotFoundException.class);

        orderController.getById(orderId);
        verify(orderService).getByIdThrowsException(orderId);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    public void getAllSuccess() {
        int listSize = ApiTestUtils.generateOrderListSize();
        List<Order> orders = ApiTestUtils.generateOrderList(listSize);
        List<OrderDTO> orderDTOList = ApiTestUtils.generateOrderDTOList(orders);

        when(orderService.getAll()).thenReturn(orders);
        when(orderMapper.toOrderDTOList(orders)).thenReturn(orderDTOList);

        ResponseEntity<List<OrderDTO>> response = orderController.getAll();
        verify(orderService).getAll();
        verify(orderMapper).toOrderDTOList(orders);
        verifyNoMoreInteractions(orderService, orderMapper);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(listSize, response.getBody().size());
    }
}
