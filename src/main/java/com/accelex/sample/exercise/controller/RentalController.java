package com.accelex.sample.exercise.controller;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void rent(@RequestBody RentalRequest request) {
        rentalService.rentVehicle(request);
    }

}
