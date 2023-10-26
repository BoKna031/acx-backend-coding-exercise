package com.accelex.sample.exercise.validator;

import com.accelex.sample.exercise.model.Rental;
import com.accelex.sample.exercise.model.enums.RentalStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RentalStatusValidator implements ConstraintValidator<ValidRentalStatus, Rental> {
    @Override
    public void initialize(ValidRentalStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Rental rental, ConstraintValidatorContext constraintValidatorContext) {
        if(rental.getReturnDate() != null){
            return rental.getStatus().equals(RentalStatus.RETURNED_OK)
                    || rental.getStatus().equals(RentalStatus.RETURNED_DAMAGED);
        }
        else
            return rental.getStatus().equals(RentalStatus.OUT)
                    || rental.getStatus().equals(RentalStatus.PENDING);
    }
}
