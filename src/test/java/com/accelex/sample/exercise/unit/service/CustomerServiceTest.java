package com.accelex.sample.exercise.unit.service;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.exception.EntityConflictException;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepositoryMock;

    @BeforeEach
    public void SetUp(){
        BDDMockito.when(customerRepositoryMock.save(ArgumentMatchers.any()))
                .thenReturn(customerWithId());
        BDDMockito.when(customerRepositoryMock.findById(customerWithId().getId()))
                .thenReturn(Optional.of(customerWithId()));
        PageImpl<Customer> customerPage = new PageImpl<>(List.of(customerWithId()));

        BDDMockito.when(customerRepositoryMock.findAll((Pageable) ArgumentMatchers.any()))
                .thenReturn(customerPage);

        BDDMockito.when(customerRepositoryMock.existsByDriverLicenseNumber(getAlreadyExistingDrivingLicense()))
                .thenReturn(true);

        BDDMockito.when(customerRepositoryMock.existsByDriverLicenseNumber(customerRequest().getDriverLicenseNumber()))
                .thenReturn(false);

        BDDMockito.when(customerRepositoryMock.findById(-1L))
                .thenReturn(Optional.empty());
    }

    private String getAlreadyExistingDrivingLicense(){
        return "11111";
    }

    @Test
    public void createSuccessfully(){
        CustomerResponse response = customerService.create(customerRequest());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(customerRequest().getFirstName(), response.getFirstName());
        assertEquals(customerRequest().getLastName(), response.getLastName());
        assertEquals(customerRequest().getDriverLicenseNumber(), response.getDriverLicenseNumber());
        assertEquals(customerRequest().getBirthDate(), response.getBirthDate());
    }



    @Test
    public void createCustomerWithExistingDrivingLicenseThrowsCustomerConflictException(){
        CustomerRequest request = customerRequest();
        String existingDrivingLicense = getAlreadyExistingDrivingLicense();
        request.setDriverLicenseNumber(existingDrivingLicense);

        assertThrows(EntityConflictException.class, () ->{
            customerService.create(request);
        });
    }

    @Test
    public void getAllReturnsPagesOfCustomerResponse(){
        Page<CustomerResponse> page = customerService.getAll(null);

        assertNotNull(page);
        assertThat(page.toList()).isNotEmpty();
        assertEquals(customerResponse().getFirstName(), page.toList().get(0).getFirstName());
        assertEquals(customerResponse().getId(), page.toList().get(0).getId());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    public void getByIdSuccessfully(){
        CustomerResponse customerResponse = customerService.getById(customerWithId().getId());
        assertNotNull(customerResponse);
        assertNotNull(customerResponse.getId());
        assertEquals(customerWithId().getId(), customerResponse.getId());
    }

    @Test
    public void getByIdCustomerThatDoesNotExistsThrowsElementNotFoundException(){
        assertThrows(ElementNotFoundException.class, () -> {
            customerService.getById(-1L);
        });
    }
}
