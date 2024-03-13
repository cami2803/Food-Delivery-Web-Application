package com.example.fooddelivery.dtos.validators;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private UUID customerid;

    private String name;
    private String email;

    public CustomerDTO(UUID customerId, String name, String email){
        this.customerid = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerid;
    }

    public void setCustomerId(UUID customerId) {
        this.customerid = customerId;
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
