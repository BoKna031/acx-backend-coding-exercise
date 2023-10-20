package com.accelex.sample.exercise.model;

import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.service.CustomerService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vehicle vehicle;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime returnDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}
