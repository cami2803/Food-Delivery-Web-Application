package com.example.fooddelivery.services;

import com.example.fooddelivery.dtos.builders.OrderBuilder;
import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Orders;
import com.example.fooddelivery.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public UUID createOrder(OrderDTO orderDTO) {
        Orders savedOrder = orderRepository.save(OrderBuilder.toEntity(orderDTO));
        return savedOrder.getOrderId();
    }

    public OrderDTO updateOrder(UUID id, OrderDTO orderDTO){
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with it: " + id));
        order.setCustomerId(orderDTO.getCustomerid());
        order.setTotal(orderDTO.getTotal());
        Orders updatedOrder = orderRepository.save(order);
        return OrderBuilder.toOrderDTO(updatedOrder);
    }

    public UUID deleteOrder(UUID id){
        orderRepository.deleteById(id);
        return id;
    }

    public OrderDTO getOrderById(UUID id){
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return OrderBuilder.toOrderDTO(order);
    }

    public List<Orders> getAllOrders(){
        List<Orders> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order1 -> orders.add(order1));
        return orders;
    }

}
