package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.entities.Customer;
import com.example.fooddelivery.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //postman verification for list: http://localhost:8080/customer/list
    @GetMapping(value = "/list")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    //postman verification for insert: http://localhost:8080/customer/insert
    //{
    //    "email": "john@example.com",
    //    "name": "John Doe"
    //}
    //will return the id
    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> createCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        UUID customerID = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerID, HttpStatus.CREATED);
    }


    //postman verification for get a customer by id: http://localhost:8080/customer/{customerid}
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") UUID customerid){
        CustomerDTO dto = customerService.getCustomerById(customerid);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //postman verification for deleting a customer by id: http://localhost:8080/customer/{customerid}
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID customerid) {
        customerService.deleteCustomer(customerid);
        return ResponseEntity.noContent().build();
    }

    //postman verification for updating a customer: http://localhost:8080/customer/{customerid}
//    {
//        "customerid": "b2157fee-efdf-4778-838b-e825b5f1354d",
//            "name": "Ana",
//            "email": "john@example.com"
//    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") UUID customerid, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(customerid, customerDTO);
        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
    }

}
