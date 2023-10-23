package com.accelex.sample.exercise.service;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import com.accelex.sample.exercise.mapper.RentalMapper;
import com.accelex.sample.exercise.mapper.VehicleMapper;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.repository.RentalRepository;
import com.accelex.sample.exercise.repository.VehicleRepository;
import com.accelex.sample.exercise.service.interfaces.IRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService implements IRentalService {

    private final RentalRepository rentalRepository;

    private final CustomerRepository customerRepository;

    private final VehicleRepository vehicleRepository;

    public RentalResponse rentVehicle(RentalRequest request){
        Rental rental = prepareRental(request);

        if(!isVehicleDriveable(request.getVehicleId()))
            throw new RentalNotPossibleException("Vehicle is broken!");

        if(!isVehicleAvailableAtTimestamp(request.getVehicleId(), rental.getStartDate()))
            throw new RentalNotPossibleException("Vehicle is not available!");

        Rental savedRental = rentalRepository.save(rental);
        return RentalMapper.mapToRentalResponse(savedRental);
    }

    public RentalResponse returnVehicle(ReturnVehicleRequest request){
        getCustomerOrThrowNotFoundException(request.getCustomerId());
        getVehicleOrThrowNotFoundException(request.getVehicleId());

        Rental rental = findRentedVehicleOrThrowNotFoundException(request.getCustomerId(), request.getVehicleId());

        rental.setReturnDate(LocalDateTime.now());
        RentalStatus status = getProperReturnedStatus(request.isVehicleReturnedDamaged());
        rental.setStatus(status);

        Rental savedRental = rentalRepository.save(rental);
        return RentalMapper.mapToRentalResponse(savedRental);
    }

    private RentalStatus getProperReturnedStatus(boolean isVehicleReturnedDamaged){
        return isVehicleReturnedDamaged? RentalStatus.RETURNED_DAMAGED : RentalStatus.RETURNED_OK;
    }

    public Page<VehicleResponse> getAllRented(Pageable pageable){
        Page<Vehicle> vehicles = rentalRepository.findAllRentedVehicles(RentalStatus.OUT, pageable);
        return vehicles.map(VehicleMapper::mapToVehicleResponse);
    }

    private Rental prepareRental(RentalRequest request){
        Customer customer = getCustomerOrThrowNotFoundException(request.getCustomerId());
        Vehicle vehicle = getVehicleOrThrowNotFoundException(request.getVehicleId());
        RentalStatus status = (request.getPendingDateTime() == null)? RentalStatus.OUT: RentalStatus.PENDING;
        LocalDateTime startDateTime = (request.getPendingDateTime() == null)? LocalDateTime.now(): request.getPendingDateTime();

        return new Rental(customer, vehicle,startDateTime, null, status);
    }

    private Rental findRentedVehicleOrThrowNotFoundException(Long customerId, Long vehicleId){
        return  rentalRepository.findRentedVehicleForCustomer(customerId, vehicleId, RentalStatus.OUT)
                .orElseThrow(() -> new ElementNotFoundException("Rental doesn't exist or vehicle is already returned"));
    }

    private Vehicle getVehicleOrThrowNotFoundException(Long vehicleId) {
        return  vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ElementNotFoundException("Vehicle", vehicleId.toString()));
    }

    private Customer getCustomerOrThrowNotFoundException(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ElementNotFoundException("Customer", customerId.toString()));
    }

    private boolean isVehicleAvailableAtTimestamp(Long vehicleId, LocalDateTime timestamp){
        List<Rental> rentals = rentalRepository.findRentalsForVehicleAndTimestamp(vehicleId, timestamp);
        return rentals.isEmpty();
    }

    private boolean isVehicleDriveable(Long vehicleId){
        return !(rentalRepository.existVehicleWithStatus(vehicleId, RentalStatus.RETURNED_DAMAGED) > 0) ;
    }

}
