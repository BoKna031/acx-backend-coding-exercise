package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.service.interfaces.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody VehicleRequest request){
        return new ResponseEntity<>(vehicleService.create(request), HttpStatus.CREATED);
    }
}
