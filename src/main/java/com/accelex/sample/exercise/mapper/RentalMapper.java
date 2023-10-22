package com.accelex.sample.exercise.mapper;

import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.model.Rental;

public class RentalMapper {

    public static RentalResponse mapToRentalResponse(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                CustomerMapper.mapToCustomerResponse(rental.getCustomer()),
                VehicleMapper.mapToVehicleResponse(rental.getVehicle()),
                rental.getStartDate(),
                rental.getReturnDate(),
                rental.getStatus().name()
        );
    }
}
