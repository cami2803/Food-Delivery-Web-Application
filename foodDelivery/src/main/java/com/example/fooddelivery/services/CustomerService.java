package com.example.fooddelivery.services;

import com.example.fooddelivery.dtos.builders.CustomerBuilder;
import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.entities.Customer;
import com.example.fooddelivery.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        Customer customer = new Customer("", "");
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());

        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getCustomerId();
    }

    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with it: " + id));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerBuilder.toCustomerDTO(updatedCustomer);
    }
    public UUID deleteCustomer(UUID id){
        customerRepository.deleteById(id);
        return id;
    }

    public CustomerDTO getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return CustomerBuilder.toCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerBuilder::toCustomerDTO)
                .collect(Collectors.toList());
    }

}
