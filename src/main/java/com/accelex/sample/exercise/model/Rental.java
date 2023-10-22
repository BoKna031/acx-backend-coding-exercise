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
    public Rental(Customer customer, Vehicle vehicle, @NotNull LocalDateTime startDate, LocalDateTime returnDate, @NotNull RentalStatus status) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
