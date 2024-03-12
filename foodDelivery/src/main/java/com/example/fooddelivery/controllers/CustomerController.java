package com.example.fooddelivery.controllers;

import com.example.fooddelivery.dtos.validators.CustomerDTO;
import com.example.fooddelivery.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/client")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {this.customerService = customerService;}

    @GetMapping(value = "list")
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> dtos = customerService.getAllCustomers();
        List<EntityModel<CustomerDTO>> customerEntities = dtos.stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(CustomerController.class).getClient(dto.getCustomerId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CustomerDTO>> customerCollectionModel = CollectionModel.of(customerEntities);
        return ResponseEntity.ok(customerCollectionModel);
    }


    @PostMapping(value = "/insert")
    public ResponseEntity<UUID> insertClient(@Valid @RequestBody CustomerDTO customerDTO){
        UUID customerID = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDTO> getClient(@PathVariable("customerID") UUID customerId){
        CustomerDTO dto = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateClient(@PathVariable UUID id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(updatedCustomerDTO, HttpStatus.OK);
    }

}
