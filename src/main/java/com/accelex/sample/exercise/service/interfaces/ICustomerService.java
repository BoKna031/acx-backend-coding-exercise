package com.accelex.sample.exercise.service.interfaces;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    CustomerResponse create(CustomerRequest customerRequest);
    Page<CustomerResponse> getAll(Pageable pageable);
    CustomerResponse getById(long id);
}
