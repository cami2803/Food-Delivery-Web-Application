package com.example.fooddelivery.dtos.builders;

import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Order;

public class OrderBuilder {
    public OrderBuilder(){}

    public static OrderDTO toOrderDTO(Order order){
        return new OrderDTO(order.getOrderId(), order.getCustomerId(), order.getTotal());
    }

    public static Order toEntity(OrderDTO orderDTO){
        return new Order(orderDTO.getCustomerId(), orderDTO.getTotal());
    }
}
