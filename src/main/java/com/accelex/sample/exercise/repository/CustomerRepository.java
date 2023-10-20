package com.accelex.sample.exercise.repository;

import com.accelex.sample.exercise.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByDriverLicenseNumber(String number);
}
