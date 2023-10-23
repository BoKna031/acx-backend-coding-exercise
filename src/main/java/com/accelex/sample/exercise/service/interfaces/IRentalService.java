package com.accelex.sample.exercise.service.interfaces;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRentalService {
    RentalResponse rentVehicle(RentalRequest request);
    RentalResponse returnVehicle(ReturnVehicleRequest request);
    Page<VehicleResponse> getAllRented(Pageable pageable);
}
