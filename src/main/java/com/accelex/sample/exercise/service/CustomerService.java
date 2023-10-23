package com.accelex.sample.exercise.service;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.exception.EntityConflictException;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.mapper.CustomerMapper;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.service.interfaces.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse create(CustomerRequest customerRequest){
        Customer customer = CustomerMapper.mapToCustomer(customerRequest);
        if(customerRepository.existsByDriverLicenseNumber(customerRequest.getDriverLicenseNumber()))
            throw new EntityConflictException("Customer with entered driving license number already exists");
        Customer savedCustomer =  customerRepository.save(customer);
        return CustomerMapper.mapToCustomerResponse(savedCustomer);
    }

    public Page<CustomerResponse> getAll(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(CustomerMapper::mapToCustomerResponse);
    }

    public CustomerResponse getById(long id) {
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ElementNotFoundException("Customer", String.valueOf(id)));
        return CustomerMapper.mapToCustomerResponse(customer);
    }
}
