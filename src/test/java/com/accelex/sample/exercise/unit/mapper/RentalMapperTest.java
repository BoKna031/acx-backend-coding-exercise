package com.accelex.sample.exercise.unit.mapper;

import com.accelex.sample.exercise.dto.rental.RentalResponse;
import com.accelex.sample.exercise.mapper.RentalMapper;
import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.util.EntityTestCreator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class RentalMapperTest {
    @Test
    void mappedRentalResponseHaveValidData() {
        Rental rental = EntityTestCreator.existingRental(RentalStatus.OUT, LocalDateTime.of(2023,11,3,15,10));
        RentalResponse mappedEntity = RentalMapper.mapToRentalResponse(rental);

        assertNotNull(mappedEntity);
        assertEquals(rental.getId(), mappedEntity.getId());
        assertNotNull(mappedEntity.getCustomer());
        assertEquals(rental.getCustomer().getId(), mappedEntity.getCustomer().getId());
        assertNotNull(mappedEntity.getVehicle());
        assertEquals(rental.getVehicle().getId(), mappedEntity.getVehicle().getId());
        assertEquals(rental.getStartDate(), mappedEntity.getStartDate());
        assertEquals(rental.getReturnDate(), mappedEntity.getReturnDate());
        assertEquals(rental.getStatus().toString(), mappedEntity.getStatus());
    }

    @Test
    void mappedRentalResponseIsValidWithNullData() {
        Rental rental = EntityTestCreator.existingRental(RentalStatus.PENDING, null);
        RentalResponse mappedEntity = RentalMapper.mapToRentalResponse(rental);

        assertNotNull(mappedEntity);
        assertEquals(rental.getId(), mappedEntity.getId());
        assertNotNull(mappedEntity.getCustomer());
        assertEquals(rental.getCustomer().getId(), mappedEntity.getCustomer().getId());
        assertNotNull(mappedEntity.getVehicle());
        assertEquals(rental.getVehicle().getId(), mappedEntity.getVehicle().getId());
        assertEquals(rental.getStartDate(), mappedEntity.getStartDate());
        assertNull(mappedEntity.getReturnDate());
        assertEquals(rental.getStatus().toString(), mappedEntity.getStatus());
    }
}
