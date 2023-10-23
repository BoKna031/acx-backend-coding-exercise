package com.accelex.sample.exercise.repository;

import com.accelex.sample.exercise.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByRegistration(String registration);
}
