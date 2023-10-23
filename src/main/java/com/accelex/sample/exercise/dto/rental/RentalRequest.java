package com.accelex.sample.exercise.dto.rental;

import com.accelex.sample.exercise.model.Customer;
import com.accelex.sample.exercise.model.Vehicle;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
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
