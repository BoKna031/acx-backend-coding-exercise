package com.accelex.sample.exercise.validator;

import com.accelex.sample.exercise.model.Rental;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartDateBeforeReturnDateValidator implements ConstraintValidator<StartDateBeforeReturnDate, Rental> {
    @Override
    public void initialize(StartDateBeforeReturnDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Rental rental, ConstraintValidatorContext ctx) {
        if(rental.getStartDate() == null || rental.getReturnDate() == null){
            return true;
        }

        return rental.getStartDate().isBefore(rental.getReturnDate());
    }
}
