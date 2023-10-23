package com.accelex.sample.exercise.dto.rental;

import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.validator.DateTimeCorrectOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@DateTimeCorrectOrder(startDate = "startDate", endDate = "returnDate")
public class RentalResponse {

    private Long id;
    private CustomerResponse customer;
    private VehicleResponse vehicle;
    private LocalDateTime startDate;
    private LocalDateTime returnDate;
    private String status;
}
