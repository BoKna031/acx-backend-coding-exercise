package com.accelex.sample.exercise.service;

import com.accelex.sample.exercise.dto.CustomerRequest;
import com.accelex.sample.exercise.dto.CustomerResponse;
import com.accelex.sample.exercise.mapper.CustomerMapper;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse create(CustomerRequest customerRequest){
        Customer customer = CustomerMapper.mapToCustomer(customerRequest);
        if(customerRepository.existsByDriverLicenseNumber(customerRequest.getDriverLicenseNumber()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer with entered driving license number already exists");
        Customer savedCustomer =  customerRepository.save(customer);
        return CustomerMapper.mapToCustomerResponse(savedCustomer);
    }

    public Page<CustomerResponse> getAll(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(CustomerMapper::mapToCustomerResponse);
    }

    public CustomerResponse getById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(CustomerMapper::mapToCustomerResponse).orElse(null);
    }
}
