package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    @PostMapping
    public ResponseEntity<RentalResponse> rent(@RequestBody RentalRequest request) {
        return new ResponseEntity<>(rentalService.rentVehicle(request), HttpStatus.CREATED);
    }

    @PostMapping("/return-vehicle")
    public ResponseEntity<RentalResponse> returnVehicle(@RequestBody ReturnVehicleRequest request){
        return new ResponseEntity<>(rentalService.returnVehicle(request), HttpStatus.OK);
    }

}
