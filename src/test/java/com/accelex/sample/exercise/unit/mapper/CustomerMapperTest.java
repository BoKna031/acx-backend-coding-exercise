package com.accelex.sample.exercise.unit.mapper;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.mapper.CustomerMapper;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.util.EntityTestCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void mappedCustomerResponseHaveValidData() {
        Customer customer = EntityTestCreator.customerWithId();
        CustomerResponse mappedEntity = CustomerMapper.mapToCustomerResponse(customer);

        assertNotNull(mappedEntity);
        assertEquals(customer.getFirstName(), mappedEntity.getFirstName());
        assertEquals(customer.getLastName(), mappedEntity.getLastName());
        assertEquals(customer.getId(), mappedEntity.getId());
        assertEquals(customer.getBirthDate(), mappedEntity.getBirthDate());
        assertEquals(customer.getDriverLicenseNumber(), mappedEntity.getDriverLicenseNumber());
    }

    @Test
    void mappedCustomerHaveValidData() {
        CustomerRequest request = EntityTestCreator.customerRequest();
        Customer customer = CustomerMapper.mapToCustomer(request);

        assertNotNull(customer);
        assertEquals(request.getFirstName(), customer.getFirstName());
        assertEquals(request.getLastName(), customer.getLastName());
        assertNull(customer.getId());
        assertEquals(request.getBirthDate(), customer.getBirthDate());
        assertEquals(request.getDriverLicenseNumber(), customer.getDriverLicenseNumber());
    }
}