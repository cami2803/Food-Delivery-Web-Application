package com.example.fooddelivery.dtos.builders;

import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.entities.Customer;

public class CustomerBuilder {
    public CustomerBuilder(){}

    public static CustomerDTO toCustomerDTO(Customer customer){
        return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getEmail());
    }

    public static Customer toEntity(CustomerDTO customerDTO){
        return new Customer(customerDTO.getName(), customerDTO.getEmail());
    }
}
