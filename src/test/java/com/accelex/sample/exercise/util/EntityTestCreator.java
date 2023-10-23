package com.accelex.sample.exercise.util;

import com.accelex.sample.exercise.dto.customer.CustomerRequest;
import com.accelex.sample.exercise.dto.customer.CustomerResponse;
import com.accelex.sample.exercise.dto.rental.RentalRequest;
import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.dto.rental.ReturnVehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleRequest;
import com.accelex.sample.exercise.dto.vehicle.VehicleResponse;
import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntityTestCreator {

    // ---------------CUSTOMER--------------------

    private static final String CUSTOMER_FIRST_NAME = "Mark";
    private static final String CUSTOMER_LAST_NAME = "Eric";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1994,5,14);
    private static final String DRIVING_LICENSE_NUMBER = "TEST02";

    private static final Long CUSTOMER_ID = 1L;

    public static Customer nonexistingCustomer(){
        return new Customer( CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, BIRTH_DATE, DRIVING_LICENSE_NUMBER);
    }

    public static Customer customerWithId(){
        Customer customer = nonexistingCustomer();
        customer.setId(CUSTOMER_ID);
        return customer;
    }

    public static CustomerRequest customerRequest(){
        return new CustomerRequest( CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, BIRTH_DATE, DRIVING_LICENSE_NUMBER);
    }

    public static CustomerResponse customerResponse(){
        return new CustomerResponse(CUSTOMER_ID, CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME, BIRTH_DATE, DRIVING_LICENSE_NUMBER);
    }

    //----------------VEHICLE------------------------

    private static final Long VEHICLE_ID = 1L;
    private static final String VEHICLE_BRAND = "Toyota";
    private static final String VEHICLE_MODEL = "Camry";
    private static final Integer VEHICLE_MANUFACTURED_YEAR = 2022;
    private static final String VEHICLE_COLOUR = "Silver";
    private static final String VEHICLE_REGISTRATION = "ABC123";
    public static Vehicle nonexistingVehicle(){
        return new Vehicle(VEHICLE_BRAND, VEHICLE_MODEL, VEHICLE_MANUFACTURED_YEAR, VEHICLE_COLOUR, VEHICLE_REGISTRATION);
    }
    public static Vehicle vehicleWithId(){
        Vehicle vehicle = nonexistingVehicle();
        vehicle.setId(VEHICLE_ID);
        return vehicle;
    }

    public static VehicleRequest vehicleRequest(){
        return new VehicleRequest(VEHICLE_BRAND, VEHICLE_MODEL, VEHICLE_MANUFACTURED_YEAR, VEHICLE_COLOUR, VEHICLE_REGISTRATION);
    }

    public static VehicleResponse vehicleResponse(){
        return new VehicleResponse(VEHICLE_ID, VEHICLE_BRAND, VEHICLE_MODEL, VEHICLE_MANUFACTURED_YEAR, VEHICLE_COLOUR, VEHICLE_REGISTRATION);
    }


    //-----------------RENTAL---------------------
    private static final Long RENTAL_ID = 2L;
    public static Rental nonexistingRental(RentalStatus status, LocalDateTime returnDateTime){
        return new Rental(
                customerWithId(),
                vehicleWithId(),
                Constants.DUMMY_LOCAL_DATE_TIME_BEFORE,
                returnDateTime,
                status
        );
    }
    public static Rental existingRental(RentalStatus status, LocalDateTime returnDateTime){
        Rental rental = nonexistingRental(status, returnDateTime);
        rental.setId(RENTAL_ID);
        return rental;
    }

    public static RentalRequest rentalRequest(){
        return new RentalRequest(CUSTOMER_ID, VEHICLE_ID, null);
    }
    public static RentalResponse rentalResponse(RentalStatus status, LocalDateTime returnDate){
        return new RentalResponse(
                RENTAL_ID,
                customerResponse(),
                vehicleResponse(),
                Constants.DUMMY_LOCAL_DATE_TIME_BEFORE,
                returnDate,
                status.name()
        );
    }

    public static ReturnVehicleRequest returnVehicleRequest(boolean isVehicleDamaged){
        return new ReturnVehicleRequest(CUSTOMER_ID,VEHICLE_ID, isVehicleDamaged);
    }
}
