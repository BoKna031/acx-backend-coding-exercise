package com.accelex.sample.exercise.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleResponse {
    private long id;
    private String brand;
    private String model;
    private int year;
    private String colour;
    private String registration;

}
