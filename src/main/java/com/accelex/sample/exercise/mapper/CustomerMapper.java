package com.accelex.sample.exercise.mapper;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.model.Customer;

public class CustomerMapper {

    public static CustomerResponse mapToCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate(),
                customer.getDriverLicenseNumber()
        );
    }

    public static Customer mapToCustomer(CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setBirthDate(customerRequest.getBirthDate());
        customer.setDriverLicenseNumber(customerRequest.getDriverLicenseNumber());
        return customer;
    }
}
