package com.accelex.sample.exercise.dto.rental;

import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class RentalResponse {

    private Long id;
    private CustomerResponse customer;
    private VehicleResponse vehicle;
    private LocalDateTime startDate;
    private LocalDateTime returnDate;
    private String status;
}
