package com.example.fooddelivery.dtos.validators;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class OrderDTO extends RepresentationModel<OrderDTO> {
    private UUID orderid;
    private UUID customerid;

    private float total;

    public OrderDTO(UUID orderId, UUID customerId, float total){
        this.orderid = orderId;
        this.customerid = customerId;
        this.total = total;
    }

    public UUID getOrderId() {
        return orderid;
    }

    public void setOrderId(UUID orderId) {
        this.orderid = orderId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public UUID getCustomerid() {
        return customerid;
    }

    public void setCustomerid(UUID customerid) {
        this.customerid = customerid;
    }


}
