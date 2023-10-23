package com.accelex.sample.exercise.unit.service;

import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.repository.VehicleRepository;
import com.accelex.sample.exercise.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static com.accelex.sample.exercise.util.EntityTestCreator.customerRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepositoryMock;

    @BeforeEach
    public void SetUp(){
        BDDMockito.when(vehicleRepositoryMock.save((ArgumentMatchers.any())))
                .thenReturn(vehicleWithId());
    }

    @Test
    public void createSuccessfully(){
        VehicleResponse response = vehicleService.create(vehicleRequest());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(vehicleRequest().getBrand(), response.getBrand());
        assertEquals(vehicleRequest().getModel(), response.getModel());
        assertEquals(vehicleRequest().getYear(), response.getYear());
        assertEquals(vehicleRequest().getColour(), response.getColour());
        assertEquals(vehicleRequest().getRegistration(), response.getRegistration());


    }
}
