package com.accelex.sample.exercise.unit.controller;

import com.accelex.sample.exercise.controller.VehicleController;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleServiceMock;

    @BeforeEach
    public void SetUp(){
        BDDMockito.when(vehicleServiceMock.create(vehicleRequest()))
                .thenReturn(vehicleResponse());
    }

    @Test
    public void create(){
        ResponseEntity<VehicleResponse> responseEntity = vehicleController.create(vehicleRequest());

        Mockito.verify(vehicleServiceMock, times(1)).create(vehicleRequest());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
