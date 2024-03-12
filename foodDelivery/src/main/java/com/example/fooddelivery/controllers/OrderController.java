package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "list")
    public ResponseEntity<CollectionModel<EntityModel<OrderDTO>>> getOrders() {
        List<OrderDTO> dtos = orderService.getAllOrders();
        List<EntityModel<OrderDTO>> orderEntities = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(OrderController.class).getOrder(dto.getOrderId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<OrderDTO>> orderCollectionModel = CollectionModel.of(orderEntities);
        return ResponseEntity.ok(orderCollectionModel);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> insertOrder(@Valid @RequestBody OrderDTO orderDTO) {
        UUID orderId = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") UUID orderId) {
        OrderDTO dto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable UUID id, @Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO updatedOrderDTO = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
    }
}

