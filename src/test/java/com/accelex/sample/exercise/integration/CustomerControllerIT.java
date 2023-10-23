package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.util.EntityTestCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.accelex.sample.exercise.util.EntityTestCreator.customerRequest;
import static com.accelex.sample.exercise.util.EntityTestCreator.customerWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private CustomerRepository customerRepositoryMock;
    @BeforeEach
    public void SetUp(){
        PageImpl<Customer> customerResponsePage = new PageImpl<>(List.of(customerWithId()));
        BDDMockito.when(customerRepositoryMock.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(customerResponsePage);

        BDDMockito.when(customerRepositoryMock.findById(customerWithId().getId()))
                .thenReturn(Optional.of(customerWithId()));

        BDDMockito.when(customerRepositoryMock.findById(-1L))
                .thenReturn(Optional.empty());

        BDDMockito.when(customerRepositoryMock.save(ArgumentMatchers.any()))
                        .thenReturn(customerWithId());

        BDDMockito.when(customerRepositoryMock.existsByDriverLicenseNumber(EXISTING_DRIVING_LICENSE))
                .thenReturn(true);
        BDDMockito.when(customerRepositoryMock.existsByDriverLicenseNumber(ArgumentMatchers.any()))
                .thenReturn(false);
    }

    private String EXISTING_DRIVING_LICENSE = "TESTTT";
    @Test
    void createCustomerSuccessfully() {
        ResponseEntity<CustomerResponse> response = testRestTemplate.postForEntity(
                "/api/customers",
                customerRequest(),
                CustomerResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createCustomerWithExistingDrivingLicenseThrowsException() {
        CustomerRequest request = customerRequest();
        request.setDriverLicenseNumber(EXISTING_DRIVING_LICENSE);
        ResponseEntity<CustomerResponse> response = testRestTemplate.postForEntity(
                "/api/customers",
                request,
                CustomerResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    void getCustomerByIdWhenCustomerExists() {
        Customer customer = customerWithId();
        ResponseEntity<CustomerResponse> response = testRestTemplate.getForEntity("/api/customers/" + customer.getId(), CustomerResponse.class);
        CustomerResponse customerResponse = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customer.getId(), customerResponse.getId());
        assertEquals(customer.getFirstName(), customerResponse.getFirstName());
        assertEquals(customer.getLastName(), customerResponse.getLastName());
        assertEquals(customer.getBirthDate(), customerResponse.getBirthDate());
        assertEquals(customer.getDriverLicenseNumber(), customerResponse.getDriverLicenseNumber());

    }

    @Test
    void getByIdWhenCustomerDoesNotExists(){
        long notExistingId = -1;
        ResponseEntity<CustomerResponse> response = testRestTemplate.exchange(
                "/api/customers/" + notExistingId,
                HttpMethod.GET,
                null,
                CustomerResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}