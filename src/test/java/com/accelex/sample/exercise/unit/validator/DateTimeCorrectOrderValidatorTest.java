package com.accelex.sample.exercise.unit.validator;

import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import com.accelex.sample.exercise.util.Constants;
import com.accelex.sample.exercise.util.EntityTestCreator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ExtendWith(SpringExtension.class)
public class DateTimeCorrectOrderValidatorTest {
    @Test
    public void testValidDates() {
        Rental rental = EntityTestCreator.nonexistingRental(RentalStatus.RETURNED_OK, Constants.DUMMY_LOCAL_DATE_TIME_BEFORE);
        rental.setReturnDate(Constants.DUMMY_LOCAL_DATE_TIME_AFTER);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(violations.isEmpty());
    }

    @Test
    public void testInvalidDates() {
        Rental rental = EntityTestCreator.nonexistingRental(RentalStatus.RETURNED_OK, Constants.DUMMY_LOCAL_DATE_TIME_AFTER);
        rental.setReturnDate(Constants.DUMMY_LOCAL_DATE_TIME_BEFORE);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rental>> violations = validator.validate(rental);

        assert(!violations.isEmpty());
    }
}
