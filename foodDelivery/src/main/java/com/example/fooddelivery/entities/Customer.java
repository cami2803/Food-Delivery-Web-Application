package com.example.fooddelivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    private UUID customerID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(){

    }

    public UUID getCustomerId() {
        return customerID;
    }

    public void setCustomerId(UUID customerId) {
        this.customerID = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
