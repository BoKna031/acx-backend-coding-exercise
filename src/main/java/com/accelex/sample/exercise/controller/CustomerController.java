package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.CustomerRequest;
import com.accelex.sample.exercise.dto.CustomerResponse;
import com.accelex.sample.exercise.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest costumer){
        return new ResponseEntity<>(customerService.createCustomer(costumer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(Pageable pageable){
        return ResponseEntity.ok(customerService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable int id){
        CustomerResponse customerResponse = customerService.getCustomer(id);
        if(customerResponse == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id: " + id + " not found");
        return ResponseEntity.ok(customerResponse);
    }
}
