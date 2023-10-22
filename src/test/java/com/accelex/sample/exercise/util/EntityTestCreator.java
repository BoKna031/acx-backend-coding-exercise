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
    public static Customer nonexistingCustomer(){
        return new Customer(
                "Mark",
                "Eric",
                LocalDate.of(1994,5,14),
                "TEST02");
    }

    public static Customer customerWithId(){
        Customer customer = nonexistingCustomer();
        customer.setId(1l);
        return customer;
    }

    public static CustomerRequest customerRequest(){
        return new CustomerRequest(
                "Tim",
                "Armelle",
                LocalDate.of(1992,4,15),
                "TT22XX"
        );
    }

    public static CustomerResponse customerResponse(){
        return new CustomerResponse(
                     1l,
                "Tim",
                "Armelle",
                LocalDate.of(1992,4,15),
                "TT22XX"
        );
    }

    //----------------VEHICLE------------------------

    public static Vehicle nonexistingVehicle(){
        return new Vehicle(
                "Toyota",
                "Camry",
                  2022,
                "Silver",
            "ABC123"
                );
    }
    public static Vehicle vehicleWithId(){
        Vehicle vehicle = nonexistingVehicle();
        vehicle.setId(2l);
        return vehicle;
    }

    public static VehicleRequest vehicleRequest(){
        return new VehicleRequest(
                "Toyota",
                "Camry",
                2022,
                "Silver",
                "ABC123"
        );
    }

    public static VehicleResponse vehicleResponse(){
        return new VehicleResponse(
                1l,
                "Toyota",
                "Camry",
                2022,
                "Silver",
                "ABC123"
        );
    }


    //-----------------RENTAL---------------------
    public static Rental nonexistingRental(RentalStatus status, LocalDateTime returnDateTime){
        return new Rental(
                customerWithId(),
                vehicleWithId(),
                LocalDateTime.of(2023,10,15,13,20),
                returnDateTime,
                status
        );
    }
    public static Rental existingRental(RentalStatus status, LocalDateTime returnDateTime){
        Rental rental = nonexistingRental(status, returnDateTime);
        rental.setId(2l);
        return rental;
    }

    public static RentalRequest rentalRequest(){
        return new RentalRequest(
                1l,
                2l,
                null
        );
    }
    public static RentalResponse rentalResponse(RentalStatus status, LocalDateTime returnDate){
        return new RentalResponse(
                2l,
                customerResponse(),
                vehicleResponse(),
                LocalDateTime.of(2023,10,15,13,20),
                returnDate,
                status.name()
        );
    }

    public static ReturnVehicleRequest returnVehicleRequest(boolean isVehicleDamaged){
        return new ReturnVehicleRequest(1l,1l,isVehicleDamaged);
    }
}
