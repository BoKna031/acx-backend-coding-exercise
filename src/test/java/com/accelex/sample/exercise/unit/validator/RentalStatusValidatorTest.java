package com.accelex.sample.exercise.unit.validator;

import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.util.Constants;
import com.accelex.sample.exercise.util.EntityTestCreator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ExtendWith(SpringExtension.class)
public class RentalStatusValidatorTest {

    @ParameterizedTest
    @EnumSource(value = RentalStatus.class, names = {"OUT", "PENDING"})
    public void testValidRentalStatusWhenReturnDateIsNull(RentalStatus status) {
        Rental rental = EntityTestCreator.nonexistingRental(status, null);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(violations.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = RentalStatus.class, names = {"RETURNED_OK", "RETURNED_DAMAGED"})
    public void testValidRentalStatusWhenReturnDateExists(RentalStatus status) {
        Rental rental = EntityTestCreator.nonexistingRental(status, Constants.DUMMY_LOCAL_DATE_TIME_AFTER);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(violations.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = RentalStatus.class, names = {"RETURNED_OK", "RETURNED_DAMAGED"})
    public void testInvalidStatusWhenReturnDateIsNull(RentalStatus status) {
        Rental rental = EntityTestCreator.nonexistingRental(status, null);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(!violations.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = RentalStatus.class, names = {"OUT", "PENDING"})
    public void testInvalidStatusWhenReturnDateExists(RentalStatus status) {
        Rental rental = EntityTestCreator.nonexistingRental(status, Constants.DUMMY_LOCAL_DATE_TIME_AFTER);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(!violations.isEmpty());
    }
}
