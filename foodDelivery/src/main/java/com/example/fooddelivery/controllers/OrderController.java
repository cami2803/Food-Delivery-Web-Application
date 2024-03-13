package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Orders;
import com.example.fooddelivery.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //postman verification for list: http://localhost:8080/orders/list
    @GetMapping(value = "/list")
    public List<Orders> getOrders() {
        return orderService.getAllOrders();
    }

    //postman verification for insert: http://localhost:8080/orders/insert
    //{
    //   "customerid": "794bd377-8e9f-4c49-9c3f-dba60cc3762e",
    //   "total": 100.00
    //}
    //will return the id
    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        UUID orderId = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    //postman verification for get an order by id: http://localhost:8080/orders/{orderid}
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") UUID orderId) {
        OrderDTO dto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //postman verification for deleting an order by id: http://localhost:8080/orders/{orderid}
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID orderid) {
        orderService.deleteOrder(orderid);
        return ResponseEntity.noContent().build();
    }

    //postman verification for updating a customer: http://localhost:8080/orders/{orderid}
    //{
    //    "customerid": "794bd377-8e9f-4c49-9c3f-dba60cc3762e",
    //    "total": 3200.00
    //}
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") UUID orderid, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(orderid, orderDTO);
        return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
    }
}

