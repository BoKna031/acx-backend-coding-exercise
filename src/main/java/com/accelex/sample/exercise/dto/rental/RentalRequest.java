package com.accelex.sample.exercise.dto.rental;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RentalRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long vehicleId;

    @FutureOrPresent
    private LocalDateTime pendingDateTime;
}
