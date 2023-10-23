package com.accelex.sample.exercise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends BaseEntity {
    @NotNull @NotEmpty
    private String brand;
    @NotNull @NotEmpty
    private String model;
    @Column(nullable = false, name = "manufacture_year")
    private int year;
    private String colour;
    @Column(unique = true, nullable = false)
    private String registration;
}
