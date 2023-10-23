package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.service.interfaces.IRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final IRentalService rentalService;
    @PostMapping
    public ResponseEntity<RentalResponse> rent(@RequestBody RentalRequest request) {
        return new ResponseEntity<>(rentalService.rentVehicle(request), HttpStatus.CREATED);
    }

    @PostMapping("/return-vehicle")
    public ResponseEntity<RentalResponse> returnVehicle(@RequestBody ReturnVehicleRequest request){
        return new ResponseEntity<>(rentalService.returnVehicle(request), HttpStatus.OK);
    }

    @GetMapping("/rented")
    public ResponseEntity<Page<VehicleResponse>> getAllRented(Pageable pageable){
        return ResponseEntity.ok(rentalService.getAllRented(pageable));
    }

}
