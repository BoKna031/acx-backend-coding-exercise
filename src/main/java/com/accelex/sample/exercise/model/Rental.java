package com.accelex.sample.exercise.model;

import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.validator.DateTimeCorrectOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@DateTimeCorrectOrder(startDate = "startDate", endDate = "returnDate")
public class Rental extends BaseEntity {

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vehicle vehicle;

    @NotNull
    @Column(name = "start_date_time")
    private LocalDateTime startDate;

    @Column(name = "return_date_time")
    private LocalDateTime returnDate;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private RentalStatus status;
}
