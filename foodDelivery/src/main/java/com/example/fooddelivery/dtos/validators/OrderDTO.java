package com.example.fooddelivery.dtos.validators;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class OrderDTO extends RepresentationModel<OrderDTO> {
    private UUID orderId;
    @NotNull
    private UUID customerId;
    @NotNull

    private float total;

    public OrderDTO(UUID orderId, UUID customerId, float total){
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }


}
