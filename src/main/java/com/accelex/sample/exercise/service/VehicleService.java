package com.accelex.sample.exercise.service;

import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.exception.EntityConflictException;
import com.accelex.sample.exercise.mapper.VehicleMapper;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.repository.VehicleRepository;
import com.accelex.sample.exercise.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleResponse create(VehicleRequest vehicleRequest){
        Vehicle vehicle = VehicleMapper.mapToVehicle(vehicleRequest);
        if(vehicleRepository.existsByRegistration(vehicle.getRegistration()))
            throw new EntityConflictException("Vehicle with entered registration already exists!");
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return VehicleMapper.mapToVehicleResponse(savedVehicle);
    }
}
