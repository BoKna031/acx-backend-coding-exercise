package com.accelex.sample.exercise.mapper;

import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.model.Vehicle;

public class VehicleMapper {
    public static Vehicle mapToVehicle(VehicleRequest request){
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setYear(request.getYear());
        vehicle.setColour(request.getColour());
        vehicle.setModel(request.getModel());
        vehicle.setRegistration(request.getRegistration());
        return vehicle;
    }

    public static VehicleResponse mapToVehicleResponse(Vehicle vehicle){
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getColour(),
                vehicle.getRegistration()
        );
    }
}
