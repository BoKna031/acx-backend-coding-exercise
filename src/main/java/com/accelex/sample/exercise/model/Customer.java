package com.accelex.sample.exercise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    private String firstName;
    @NotNull @NotEmpty
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    @NotNull @NotEmpty
    private String driverLicenseNumber;

    public Customer(@NotNull String firstName, @NotNull String lastName, @NotNull LocalDate birthDate, @NotNull String driverLicenseNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.driverLicenseNumber = driverLicenseNumber;
    }
}
