package com.accelex.sample.exercise.unit.mapper;


import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.mapper.VehicleMapper;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.util.EntityTestCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleMapperTest {

    @Test
    public void mappedVehicleResponseHaveValidData(){
        Vehicle vehicle = EntityTestCreator.vehicleWithId();

        VehicleResponse mappedEntity = VehicleMapper.mapToVehicleResponse(vehicle);

        assertNotNull(mappedEntity);
        assertEquals(vehicle.getId(), mappedEntity.getId());
        assertEquals(vehicle.getBrand(), mappedEntity.getBrand());
        assertEquals(vehicle.getModel(), mappedEntity.getModel());
        assertEquals(vehicle.getYear(), mappedEntity.getYear());
        assertEquals(vehicle.getColour(), mappedEntity.getColour());
        assertEquals(vehicle.getRegistration(), mappedEntity.getRegistration());
    }

    @Test
    public void mappedVehicleHaveValidData(){
        VehicleRequest request = EntityTestCreator.vehicleRequest();
        Vehicle vehicle = VehicleMapper.mapToVehicle(request);

        assertNotNull(vehicle);
        assertNull(vehicle.getId());
        assertEquals(request.getBrand(), vehicle.getBrand());
        assertEquals(request.getModel(), vehicle.getModel());
        assertEquals(request.getYear(), vehicle.getYear());
        assertEquals(request.getColour(), vehicle.getColour());
        assertEquals(request.getRegistration(), vehicle.getRegistration());

    }
}
