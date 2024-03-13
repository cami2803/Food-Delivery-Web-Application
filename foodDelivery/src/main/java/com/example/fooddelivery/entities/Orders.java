package com.example.fooddelivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Orders implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID orderid;

    @Column(name = "customerid", nullable = false)
    private UUID customerid;

    @Column(name = "total", nullable = false)
    private float total;

    public Orders(UUID customerID, float total) {
        this.customerid = customerID;
        this.total = total;
    }

    public Orders(){

    }
    public UUID getOrderId() {
        return orderid;
    }

    public void setOrderId(UUID orderId) {
        this.orderid = orderId;
    }

    public UUID getCustomerId() {
        return customerid;
    }

    public void setCustomerId(UUID customerId) {
        this.customerid = customerId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
