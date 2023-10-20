package com.accelex.sample.exercise.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerRequest {

    @NotNull(message = "firstName is required field")
    @NotEmpty(message = "firstName cannot be empty")
    private String firstName;
    @NotNull(message = "lastName is required field")
    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @NotNull @NotEmpty
    private String driverLicenseNumber;
}
