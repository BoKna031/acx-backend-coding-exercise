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
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty
    private String brand;
    @NotNull @NotEmpty
    private String model;
    @NotNull
    @Column(name = "manufacture_year")
    private int year;
    private String colour;
    private String registration;

    public Vehicle(@NotNull String brand, @NotNull String model, @NotNull int year, String colour, String registration) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.colour = colour;
        this.registration = registration;
    }
}
