package com.example.orderservice.api;

import com.example.orderservice.dto.CartDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.mapper.CartMapper;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Cart;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.impl.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @PostMapping(value = "/create")
    @CircuitBreaker(name = "createOrderAPI")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid CartDTO cartDTO) {
        Cart cart = cartMapper.toCart(cartDTO);
        Order created = orderService.create(cart, cartMapper.toCartClient(cart), orderMapper.toOrder(cart));
        return new ResponseEntity<>(orderMapper.toOrderDTO(created), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{orderId}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> getById(@PathVariable int orderId) {
        Order toMonitor = orderService.getByIdThrowsException(orderId);
        return new ResponseEntity<>(orderMapper.toOrderDTO(toMonitor), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<Order> orders = orderService.getAll();
        return new ResponseEntity<>(orderMapper.toOrderDTOList(orders), HttpStatus.OK);
    }
}
