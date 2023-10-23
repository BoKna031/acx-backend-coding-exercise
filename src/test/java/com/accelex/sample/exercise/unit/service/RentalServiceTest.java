package com.accelex.sample.exercise.unit.service;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.repository.RentalRepository;
import com.accelex.sample.exercise.repository.VehicleRepository;
import com.accelex.sample.exercise.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static com.accelex.sample.exercise.util.EntityTestCreator.customerWithId;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private VehicleRepository vehicleRepositoryMock;

    private Customer customerWithId = customerWithId();
    private Rental createdRental = existingRental(RentalStatus.OUT, null);

    private Vehicle vehicleWithId = vehicleWithId();
    @BeforeEach
    public void SetUp(){

        BDDMockito.when(rentalRepositoryMock.save(ArgumentMatchers.any()))
                        .thenReturn(createdRental);

        BDDMockito.when(customerRepositoryMock.findById(customerWithId.getId()))
                .thenReturn(Optional.of(customerWithId));
        BDDMockito.when(customerRepositoryMock.findById(-1L))
                .thenReturn(Optional.empty());
        Vehicle brokenVehicle = vehicleWithId();
        brokenVehicle.setId(BROKEN_VEHICLE_ID);
        BDDMockito.when(vehicleRepositoryMock.findById(BROKEN_VEHICLE_ID))
                .thenReturn(Optional.of(brokenVehicle));

        BDDMockito.when(vehicleRepositoryMock.findById(vehicleWithId.getId()))
                .thenReturn(Optional.of(vehicleWithId));
        BDDMockito.when(vehicleRepositoryMock.findById(-1L))
                .thenReturn(Optional.empty());

        BDDMockito.when(rentalRepositoryMock.existVehicleWithStatus(vehicleWithId.getId(), RentalStatus.RETURNED_DAMAGED))
                .thenReturn(0);

        BDDMockito.when(rentalRepositoryMock.existVehicleWithStatus(BROKEN_VEHICLE_ID, RentalStatus.RETURNED_DAMAGED))
                .thenReturn(1);


        BDDMockito.when(rentalRepositoryMock.findRentalsForVehicleAndTimestamp(vehicleWithId.getId(), AVAILABLE_RENTAL_DATE_TIME))
                .thenReturn(Collections.emptyList());

        BDDMockito.when(rentalRepositoryMock.findRentalsForVehicleAndTimestamp(vehicleWithId.getId(), NOT_AVAILABLE_RENTAL_DATE_TIME))
                .thenReturn(List.of(existingRental(RentalStatus.OUT, null)));
    }

    private final LocalDateTime AVAILABLE_RENTAL_DATE_TIME = LocalDateTime.of(2023, 10,3,10,0);

    private final LocalDateTime NOT_AVAILABLE_RENTAL_DATE_TIME = LocalDateTime.of(2023, 11,3,10,0);
    private final long BROKEN_VEHICLE_ID = 3L;

    @Test
    public void rentVehicleSuccessfully(){
        RentalResponse response = rentalService.rentVehicle(rentalRequest());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(rentalRequest().getVehicleId(), response.getVehicle().getId());
        assertEquals(rentalRequest().getCustomerId(), response.getCustomer().getId());
        assertNotNull(response.getStartDate());
        assertNull(response.getReturnDate());
        assertNotEquals(RentalStatus.RETURNED_OK.toString(), response.getStatus());
        assertNotEquals(RentalStatus.RETURNED_DAMAGED.toString(), response.getStatus());
    }

    @Test
    public void rentWhenCustomerDoesNotExistsThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setCustomerId(-1L);
        assertThrows(ElementNotFoundException.class, () ->{
            rentalService.rentVehicle(request);
        });
    }

    @Test
    public void rentWhenVehicleDoesNotExistsThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(-1L);
        assertThrows(ElementNotFoundException.class, () ->{
            rentalService.rentVehicle(request);
        });
    }

    @Test
    public void rentWhenVehicleAreBrokenThrowsRentalNotPossibleException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(BROKEN_VEHICLE_ID);
        assertThrows(RentalNotPossibleException.class, () ->{
            rentalService.rentVehicle(request);
        });
    }

    @Test
    public void rentWhenVehicleNotAvailableThrowsRentalNotPossibleException(){
        RentalRequest request = rentalRequest();
        request.setPendingDateTime(NOT_AVAILABLE_RENTAL_DATE_TIME);
        assertThrows(RentalNotPossibleException.class, () ->{
            rentalService.rentVehicle(request);
        });
    }



}
