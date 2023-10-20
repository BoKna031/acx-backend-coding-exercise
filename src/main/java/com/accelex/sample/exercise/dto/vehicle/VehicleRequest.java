package com.accelex.sample.exercise.dto.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleRequest {

    @NotNull @NotEmpty
    private String brand;
    @NotNull @NotEmpty
    private String model;
    @NotNull
    private int year;
    private String colour;
    private String registration;
}
