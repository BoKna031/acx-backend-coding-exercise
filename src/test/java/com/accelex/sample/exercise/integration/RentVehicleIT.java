package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.repository.RentalRepository;
import com.accelex.sample.exercise.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.accelex.sample.exercise.util.Constants.*;
import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentVehicleIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private CustomerRepository customerRepositoryMock;
    @MockBean
    private RentalRepository rentalRepositoryMock;
    @MockBean
    private VehicleRepository vehicleRepositoryMock;

    @BeforeEach
    void SetUp(){

        BDDMockito.when(rentalRepositoryMock.save(nonexistingRental(RentalStatus.OUT , ArgumentMatchers.any())))
                .thenReturn(existingRental(RentalStatus.OUT, DUMMY_LOCAL_DATE_TIME_BEFORE));

        Rental returnedVehicle = existingRental(RentalStatus.RETURNED_OK, DUMMY_LOCAL_DATE_TIME_BEFORE);

        returnedVehicle.setReturnDate(DUMMY_LOCAL_DATE_TIME_AFTER);
        BDDMockito.when(rentalRepositoryMock.save(existingRental(RentalStatus.OUT ,DUMMY_LOCAL_DATE_TIME_BEFORE)))
                .thenReturn(returnedVehicle);

        BDDMockito.when(customerRepositoryMock.findById(customerWithId().getId()))
                .thenReturn(Optional.of(customerWithId()));
        BDDMockito.when(customerRepositoryMock.findById(ELEMENT_NOT_EXIST_ID))
                .thenReturn(Optional.empty());

        BDDMockito.when(vehicleRepositoryMock.findById(vehicleWithId().getId()))
                .thenReturn(Optional.of(vehicleWithId()));

        MockGetVehicleById(NOT_AVAILABLE_VEHICLE_ID);
        MockGetVehicleById(BROKEN_VEHICLE_ID);

        BDDMockito.when(vehicleRepositoryMock.findById(ELEMENT_NOT_EXIST_ID))
                .thenReturn(Optional.empty());

        BDDMockito.when(rentalRepositoryMock.existVehicleWithStatus(BROKEN_VEHICLE_ID, RentalStatus.RETURNED_DAMAGED))
                .thenReturn(1);
        BDDMockito.when(rentalRepositoryMock.existVehicleWithStatus(vehicleWithId().getId(), RentalStatus.RETURNED_DAMAGED))
                .thenReturn(0);

        BDDMockito.when(rentalRepositoryMock.findRentalsForVehicleAndTimestamp(NOT_AVAILABLE_VEHICLE_ID, DUMMY_LOCAL_DATE_TIME_AFTER))
                .thenReturn(List.of(existingRental(RentalStatus.OUT, DUMMY_LOCAL_DATE_TIME_BEFORE)));

        BDDMockito.when(rentalRepositoryMock.findRentalsForVehicleAndTimestamp(vehicleWithId().getId(), DUMMY_LOCAL_DATE_TIME_AFTER))
                .thenReturn(Collections.emptyList());

        BDDMockito.when(rentalRepositoryMock.findRentedVehicleForCustomer(DUMMY_CUSTOMER_ID, DUMMY_VEHICLE_ID, RentalStatus.OUT))
                .thenReturn(Optional.empty());

        BDDMockito.when(rentalRepositoryMock.findRentedVehicleForCustomer(customerWithId().getId(), vehicleWithId().getId(), RentalStatus.OUT))
                .thenReturn(Optional.of(existingRental(RentalStatus.OUT, DUMMY_LOCAL_DATE_TIME_BEFORE)));

    }

    private void MockGetVehicleById(Long id) {
        Vehicle notAvailableVehicle = vehicleWithId();
        notAvailableVehicle.setId(id);
        BDDMockito.when(vehicleRepositoryMock.findById(id))
                .thenReturn(Optional.of(notAvailableVehicle));
    }

    @Test
    void rentVehicleSuccessfully(){
        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals",
                rentalRequest(),
                RentalResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    void rentVehicleWithBrokenVehicleThrowsRentalNotPossibleException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(BROKEN_VEHICLE_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void rentVehicleWithNotExistingCustomerThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setCustomerId(ELEMENT_NOT_EXIST_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void rentVehicleThatDoesNotExistThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(ELEMENT_NOT_EXIST_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void rentVehicleThatIsNotAvailableThrowsRentalNotPossibleException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(NOT_AVAILABLE_VEHICLE_ID);
        request.setPendingDateTime(DUMMY_LOCAL_DATE_TIME_AFTER);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
