package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.entities.Customer;
import com.example.fooddelivery.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {this.customerService = customerService;}

    @GetMapping(value = "/list")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> createCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        UUID customerID = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") UUID customerid){
        CustomerDTO dto = customerService.getCustomerById(customerid);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID customerid) {
        customerService.deleteCustomer(customerid);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") UUID customerid, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(customerid, customerDTO);
        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
    }

}
