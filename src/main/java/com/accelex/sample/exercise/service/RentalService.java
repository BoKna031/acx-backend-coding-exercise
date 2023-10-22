package com.accelex.sample.exercise.service;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.repository.RentalRepository;
import com.accelex.sample.exercise.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    private final CustomerRepository customerRepository;

    private final VehicleRepository vehicleRepository;

    public void rentVehicle(RentalRequest request) throws RentalNotPossibleException {
        Rental rental = prepareRental(request);

        if(!isVehicleDriveable(rental.getVehicle()))
            throw new RentalNotPossibleException("Vehicle is broken!");

        if(!isVehicleAvailableAtTimestamp(rental.getVehicle(), rental.getStartDate()))
            throw new RentalNotPossibleException("Vehicle is not available!");

        rentalRepository.save(rental);
    }

    private Rental prepareRental(RentalRequest request){
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ElementNotFoundException("Customer", request.getCustomerId().toString()));
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ElementNotFoundException("Vehicle", request.getVehicleId().toString()));
        RentalStatus status = (request.getPendingDateTime() == null)? RentalStatus.OUT: RentalStatus.PENDING;
        LocalDateTime startDateTime = (request.getPendingDateTime() == null)? LocalDateTime.now(): request.getPendingDateTime();

        return new Rental(customer, vehicle,startDateTime, null, status);
    }

    private boolean isVehicleAvailableAtTimestamp(Vehicle vehicle, LocalDateTime timestamp){
        List<Rental> rentals = rentalRepository.findRentalsForVehicleAndTimestamp(vehicle, timestamp);
        return rentals.isEmpty();
    }

    private boolean isVehicleDriveable(Vehicle vehicle){
        return !(rentalRepository.existVehicleWithStatus(vehicle, RentalStatus.RETURNED_DAMAGED) > 0) ;
    }

}
