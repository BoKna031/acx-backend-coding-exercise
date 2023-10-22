package com.accelex.sample.exercise.unit.controller;

import com.accelex.sample.exercise.controller.RentalController;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.exception.ElementNotFoundException;
import com.accelex.sample.exercise.exception.RentalNotPossibleException;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.accelex.sample.exercise.util.EntityTestCreator.*;
import static com.accelex.sample.exercise.util.EntityTestCreator.rentalRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RentalControllerTest {

    @InjectMocks
    private RentalController rentalController;

    @Mock
    private RentalService rentalServiceMock;

    @BeforeEach
    public void SetUp(){
        BDDMockito.when(rentalServiceMock.rentVehicle(rentalRequest()))
                .thenReturn(rentalResponse(RentalStatus.OUT, null));

        PageImpl<VehicleResponse> rentedVehiclesPage = new PageImpl<>(List.of(vehicleResponse()));
        BDDMockito.when(rentalServiceMock.getAllRented(ArgumentMatchers.any()))
                .thenReturn(rentedVehiclesPage);

    }

    private void MockReturnVehicleRequest(boolean isVehicleDamaged){
        BDDMockito.when(rentalServiceMock.returnVehicle(returnVehicleRequest(isVehicleDamaged)))
                .thenReturn(rentalResponse(getStatus(isVehicleDamaged), LocalDateTime.of(2023,10,17,13,20)));
    }

    private RentalStatus getStatus(boolean isVehicleDamaged){
        return isVehicleDamaged? RentalStatus.RETURNED_DAMAGED: RentalStatus.RETURNED_OK;
    }

    @Test
    public void rentVehicleSuccessfully(){
        ResponseEntity<RentalResponse> response = rentalController.rent(rentalRequest());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Mockito.verify(rentalServiceMock, times(1)).rentVehicle(rentalRequest());
    }

    @Test
    public void rentVehicleFailDueToNotExistingCustomer(){
        RentalRequest notValidCustomerRequest = rentalRequest();
        notValidCustomerRequest.setCustomerId(-1L);

        BDDMockito.when(rentalServiceMock.rentVehicle(notValidCustomerRequest))
                .thenThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> {
            rentalController.rent(notValidCustomerRequest);
        });

    }

    @Test
    public void rentVehicleFailDueToNotExistingVehicle(){
        RentalRequest notValidCustomerRequest = rentalRequest();
        notValidCustomerRequest.setVehicleId(-1L);

        BDDMockito.when(rentalServiceMock.rentVehicle(notValidCustomerRequest))
                .thenThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> {
            rentalController.rent(notValidCustomerRequest);
        });
    }

    @Test
    public void rentVehicleThatIsBrokenThrowsRentalNotPossibleException(){
        RentalRequest brokenVehicleRentalRequest = rentalRequest();
        brokenVehicleRentalRequest.setVehicleId(12L);

        BDDMockito.when(rentalServiceMock.rentVehicle(brokenVehicleRentalRequest))
                .thenThrow(RentalNotPossibleException.class);

        assertThrows(RentalNotPossibleException.class, () -> {
            rentalController.rent(brokenVehicleRentalRequest);
        });
    }

    @Test
    public void rentVehicleThatIsNotAvailableThrowsRentalNotPossibleException(){
        RentalRequest notAvailableVehicleRentalRequest = rentalRequest();
        notAvailableVehicleRentalRequest.setVehicleId(11L);

        BDDMockito.when(rentalServiceMock.rentVehicle(notAvailableVehicleRentalRequest))
                .thenThrow(RentalNotPossibleException.class);

        assertThrows(RentalNotPossibleException.class, () -> {
            rentalController.rent(notAvailableVehicleRentalRequest);
        });
    }


    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void returnVehicleSuccessfully(boolean isVehicleDamaged){
        MockReturnVehicleRequest(isVehicleDamaged);

        ReturnVehicleRequest request = returnVehicleRequest(isVehicleDamaged);
        ResponseEntity<RentalResponse> response = rentalController.returnVehicle(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(rentalServiceMock, times(1)).returnVehicle(request);
        assertNotNull(response.getBody());
        assertEquals(request.getVehicleId(), response.getBody().getVehicle().getId());
        assertEquals(request.getCustomerId(), response.getBody().getCustomer().getId());
        assertEquals(getStatus(isVehicleDamaged).toString(), response.getBody().getStatus());
    }



    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void returnVehicleThatIsNotRentedByCustomerThrowsNotFoundException(boolean isVehicleDamaged){
        ReturnVehicleRequest request = returnVehicleRequest(isVehicleDamaged);
        request.setCustomerId(2L);

        BDDMockito.when(rentalServiceMock.returnVehicle(request))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            rentalController.returnVehicle(request);
        });
    }
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void returnVehicleForCustomerThatDoesNotExistThrowsNotFoundException(boolean isVehicleDamaged){
        ReturnVehicleRequest request = returnVehicleRequest(isVehicleDamaged);
        request.setCustomerId(-1L);

        BDDMockito.when(rentalServiceMock.returnVehicle(request))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            rentalController.returnVehicle(request);
        });
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void returnVehicleThatDoesNotExistThrowsNotFoundException(boolean isVehicleDamaged){
        ReturnVehicleRequest request = returnVehicleRequest(isVehicleDamaged);
        request.setVehicleId(-1L);

        BDDMockito.when(rentalServiceMock.returnVehicle(request))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            rentalController.returnVehicle(request);
        });

    }

    @Test
    public void listAllRentedVehiclesAsPage(){
        ResponseEntity<Page<VehicleResponse>> response = rentalController.getAllRented(null);
        Page<VehicleResponse> rentalPage = response.getBody();
        assertNotNull(rentalPage);
        Assertions.assertThat(rentalPage.toList()).isNotEmpty();
        assertEquals(vehicleResponse().getId(), rentalPage.toList().get(0).getId());
        assertEquals(1, rentalPage.getTotalPages());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
