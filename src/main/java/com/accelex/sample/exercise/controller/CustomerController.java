package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.service.interfaces.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest costumer){
        return new ResponseEntity<>(customerService.create(costumer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(Pageable pageable){
        return ResponseEntity.ok(customerService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable long id){
        return ResponseEntity.ok(customerService.getById(id));
    }
}
