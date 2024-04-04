package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Orders;
import com.example.fooddelivery.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/list")
    public List<Orders> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        UUID orderId = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") UUID orderId) {
        OrderDTO dto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID orderid) {
        orderService.deleteOrder(orderid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") UUID orderid, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderid, orderDTO);
        return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
    }
}

