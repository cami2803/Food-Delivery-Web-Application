package com.example.fooddelivery.dtos.builders;

import com.example.fooddelivery.dtos.validators.OrderDTO;
import com.example.fooddelivery.entities.Orders;

public class OrderBuilder {
    public OrderBuilder(){}

    public static OrderDTO toOrderDTO(Orders order){
        return new OrderDTO(order.getOrderId(), order.getCustomerId(), order.getTotal());
    }

    public static Orders toEntity(OrderDTO orderDTO){
        return new Orders(orderDTO.getCustomerid(), orderDTO.getTotal());
    }
}
