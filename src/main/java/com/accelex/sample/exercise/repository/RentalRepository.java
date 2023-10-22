package com.accelex.sample.exercise.repository;

import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.Vehicle;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r " +
            "WHERE r.vehicle = :vehicle " +
            "AND (r.returnDate IS NULL OR (:timestamp BETWEEN r.startDate AND r.returnDate))")
    List<Rental> findRentalsForVehicleAndTimestamp(
            @Param("vehicle") Vehicle vehicle,
            @Param("timestamp") LocalDateTime timestamp);

    @Query("SELECT COUNT(r) FROM Rental r WHERE r.vehicle = :vehicle AND r.status = :status")
    int existVehicleWithStatus(
            @Param("vehicle") Vehicle vehicle, @Param("status") RentalStatus status);

    @Query("SELECT r FROM Rental r WHERE r.vehicle = :vehicle AND r.customer = :customer AND r.returnDate IS NULL AND r.status = :status")
    Optional<Rental> findRentedVehicleForCustomer(
            @Param("customer") Customer customer,
            @Param("vehicle") Vehicle vehicle,
            @Param("status") RentalStatus status
    );
}
