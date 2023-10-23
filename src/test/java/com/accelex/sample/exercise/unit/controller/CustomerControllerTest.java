package com.accelex.sample.exercise.unit.controller;

import com.accelex.sample.exercise.controller.CustomerController;
import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.exception.EntityConflictException;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.accelex.sample.exercise.util.EntityTestCreator.customerRequest;
import static com.accelex.sample.exercise.util.EntityTestCreator.customerResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerServiceMock;

    @BeforeEach
    public void SetUp(){
        PageImpl<CustomerResponse> customerResponsePage = new PageImpl<>(List.of(customerResponse()));

        BDDMockito.when(customerServiceMock.getAll(ArgumentMatchers.any()))
                .thenReturn(customerResponsePage);

        BDDMockito.when(customerServiceMock.getById(1L))
                .thenReturn(customerResponse());

        BDDMockito.when(customerServiceMock.create(customerRequest()))
                        .thenReturn(customerResponse());
    }
    @Test
    void createCustomerCallServiceOnlyOneTime() {
        ResponseEntity<CustomerResponse> responseEntity = customerController.create(customerRequest());

        Mockito.verify(customerServiceMock, times(1)).create(customerRequest());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void getAllReturnsPagesOfCustomerResponse() {
        String expectedFirstName = customerResponse().getFirstName();

        ResponseEntity<Page<CustomerResponse>> response = customerController.getAll(null);
        Page<CustomerResponse> customerPage = response.getBody();
        assertNotNull(customerPage);
        assertThat(customerPage.toList()).isNotEmpty();
        assertEquals(expectedFirstName, customerPage.toList().get(0).getFirstName());
        assertEquals(1, customerPage.getTotalPages());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCustomerByIdWhenCustomerExists() {
        Long expectedCustomerId = customerResponse().getId();

        ResponseEntity<CustomerResponse> response = customerController.getById(expectedCustomerId);
        CustomerResponse foundCustomer = response.getBody();
        assertNotNull(foundCustomer);
        assertNotNull(foundCustomer.getId());
        assertEquals(foundCustomer.getId(), expectedCustomerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
   }

    @Test
    void createCustomerWithAlreadyExistingDriverLicenseNumberThrowsCustomerConflictException(){
       CustomerRequest request = customerRequest();
       request.setDriverLicenseNumber("111111");

       BDDMockito.when(customerServiceMock.create(request))
               .thenThrow(EntityConflictException.class);

       assertThrows(EntityConflictException.class, () ->{
           customerController.create(request);
       });
   }

   @Test
    void getCustomerThatDoesNotExistsThrowsElementNotFoundException(){
        BDDMockito.when(customerServiceMock.getById(-1L))
                .thenThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> {
            customerController.getById(-1L);
        });
   }
}