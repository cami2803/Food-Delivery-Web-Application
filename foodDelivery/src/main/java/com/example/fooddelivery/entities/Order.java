package com.example.fooddelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    private UUID orderID;
    private UUID customerID;
    private float total;

    public Order(UUID customerID, float total) {
        this.customerID = customerID;
        this.total = total;
    }

    public Order(){

    }
    public UUID getOrderId() {
        return orderID;
    }

    public void setOrderId(UUID orderId) {
        this.orderID = orderId;
    }

    public UUID getCustomerId() {
        return customerID;
    }

    public void setCustomerId(UUID customerId) {
        this.customerID = customerId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
