package com.accelex.sample.exercise.service.interfaces;

import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;

public interface IVehicleService {
    VehicleResponse create(VehicleRequest vehicleRequest);
}
