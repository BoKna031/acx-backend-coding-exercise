package com.accelex.sample.exercise.integration;

import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.repository.CustomerRepository;
import com.accelex.sample.exercise.repository.RentalRepository;
import com.accelex.sample.exercise.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.accelex.sample.exercise.util.Constants.*;
import static com.accelex.sample.exercise.util.Constants.DUMMY_LOCAL_DATE_TIME_BEFORE;
import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReturnVehicleIT {

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
        BDDMockito.when(customerRepositoryMock.findById(customerWithId().getId()))
                .thenReturn(Optional.of(customerWithId()));
        BDDMockito.when(customerRepositoryMock.findById(ELEMENT_NOT_EXIST_ID))
                .thenReturn(Optional.empty());

        BDDMockito.when(vehicleRepositoryMock.findById(vehicleWithId().getId()))
                .thenReturn(Optional.of(vehicleWithId()));
        BDDMockito.when(vehicleRepositoryMock.findById(ELEMENT_NOT_EXIST_ID))
                .thenReturn(Optional.empty());

        BDDMockito.when(rentalRepositoryMock.findRentedVehicleForCustomer(DUMMY_CUSTOMER_ID, DUMMY_VEHICLE_ID, RentalStatus.OUT))
                .thenReturn(Optional.empty());

        BDDMockito.when(rentalRepositoryMock.findRentedVehicleForCustomer(customerWithId().getId(), vehicleWithId().getId(), RentalStatus.OUT))
                .thenReturn(Optional.of(existingRental(RentalStatus.OUT, DUMMY_LOCAL_DATE_TIME_BEFORE)));
  }

    private void mockReturnVehicleLogic(boolean isVehicleDamaged) {
        RentalStatus status = getReturnedVehicleStatus(isVehicleDamaged);
        Rental returnedVehicle = existingRental(status, DUMMY_LOCAL_DATE_TIME_BEFORE);
        returnedVehicle.setReturnDate(DUMMY_LOCAL_DATE_TIME_AFTER);
        BDDMockito.when(rentalRepositoryMock.save(ArgumentMatchers.any(Rental.class)))
                .thenReturn(returnedVehicle);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void returnVehicleSuccessfully(boolean isVehicleDamaged){
        mockReturnVehicleLogic(isVehicleDamaged);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals/return-vehicle",
                returnVehicleRequest(isVehicleDamaged),
                RentalResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getReturnDate());
        assertEquals(getReturnedVehicleStatus(isVehicleDamaged).toString(), response.getBody().getStatus() );
    }
    @Test
    void returnVehicleWhenCustomerDoesNotExistThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setCustomerId(ELEMENT_NOT_EXIST_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals/return-vehicle",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void returnVehicleThatDoesNotExistThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(ELEMENT_NOT_EXIST_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals/return-vehicle",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void customerDidNotRentTheVehicleThrowsElementNotFoundException(){
        RentalRequest request = rentalRequest();
        request.setVehicleId(DUMMY_VEHICLE_ID);
        request.setCustomerId(DUMMY_CUSTOMER_ID);

        ResponseEntity<RentalResponse> response = testRestTemplate.postForEntity(
                "/api/rentals/return-vehicle",
                request,
                RentalResponse.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private static RentalStatus getReturnedVehicleStatus(boolean isVehicleDamaged) {
        return isVehicleDamaged ? RentalStatus.RETURNED_DAMAGED: RentalStatus.RETURNED_OK;
    }

}
