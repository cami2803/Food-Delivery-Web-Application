package com.example.fooddelivery.services;

import com.example.fooddelivery.dtos.builders.CustomerBuilder;
import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.entities.Customer;
import com.example.fooddelivery.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public UUID createCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(CustomerBuilder.toEntity(customerDTO));
        return savedCustomer.getCustomerId();
    }

    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with it: " + id));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerBuilder.toCustomerDTO(updatedCustomer);
    }
    public void deleteCustomer(UUID id){
        customerRepository.deleteById(id);
    }

    public CustomerDTO getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return CustomerBuilder.toCustomerDTO(customer);
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<Customer>();
        customerRepository.findAll().forEach(customer1 -> customers.add(customer1));
        return customers;
    }

}
