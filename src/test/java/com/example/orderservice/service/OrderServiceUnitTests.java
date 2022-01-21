package com.example.orderservice.service;

import com.example.orderservice.feign.BookFeign;
import com.example.orderservice.feign.UserFeign;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.impl.OrderService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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


}
