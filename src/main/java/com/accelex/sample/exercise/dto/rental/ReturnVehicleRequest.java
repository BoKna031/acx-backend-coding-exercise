package com.accelex.sample.exercise.dto.rental;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnVehicleRequest {
    @NotNull
    private Long customerId;

    @NotNull
    private Long vehicleId;

    @NotNull
    private boolean vehicleReturnedDamaged;
}
