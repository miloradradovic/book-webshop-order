package com.example.orderservice.api;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.mapper.CartMapper;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.impl.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @PostMapping(value = "/create-order")
    @CircuitBreaker(name = "createOrderAPI")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CartDTO cartDTO) {
        Order created = orderService.createOrder(cartMapper.toCart(cartDTO));
        return new ResponseEntity<>(orderMapper.toOrderDTO(created), HttpStatus.OK);
    }

    @GetMapping(value = "/monitor-order/{orderId}")
    public ResponseEntity<OrderDTO> monitorOrder(@PathVariable int orderId) {
        Order toMonitor = orderService.findById(orderId);
        return new ResponseEntity<>(orderMapper.toOrderDTO(toMonitor), HttpStatus.OK);
    }
}
