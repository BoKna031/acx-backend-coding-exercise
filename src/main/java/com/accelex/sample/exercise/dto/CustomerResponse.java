package com.accelex.sample.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String driverLicenseNumber;
}
