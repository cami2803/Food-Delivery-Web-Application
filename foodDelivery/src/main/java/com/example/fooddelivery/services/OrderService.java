package com.example.fooddelivery.services;

import com.example.fooddelivery.dtos.builders.OrderBuilder;
import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Order;
import com.example.fooddelivery.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public UUID createOrder(OrderDTO orderDTO) {
        Order savedOrder = orderRepository.save(OrderBuilder.toEntity(orderDTO));
        return savedOrder.getOrderId();
    }

    public OrderDTO updateOrder(UUID id, OrderDTO orderDTO){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with it: " + id));
        order.setCustomerId(orderDTO.getCustomerId());
        order.setTotal(orderDTO.getTotal());
        Order updatedOrder = orderRepository.save(order);
        return OrderBuilder.toOrderDTO(updatedOrder);
    }

    public UUID deleteOrder(UUID id){
        orderRepository.deleteById(id);
        return id;
    }

    public OrderDTO getOrderById(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return OrderBuilder.toOrderDTO(order);
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderBuilder::toOrderDTO)
                .collect(Collectors.toList());
    }

}
