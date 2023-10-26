package com.accelex.sample.exercise.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class DateTimeCorrectOrderValidator implements ConstraintValidator<DateTimeCorrectOrder, Object> {

    private String startDate;
    private String endDate;
    @Override
    public void initialize(DateTimeCorrectOrder constraintAnnotation) {
        startDate = constraintAnnotation.startDate();
        endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext ctx) {
        if(object == null){
            return true;
        }
        try {
            Field startDateField = object.getClass().getDeclaredField(startDate);
            Field endDateField = object.getClass().getDeclaredField(endDate);

            startDateField.setAccessible(true);
            endDateField.setAccessible(true);

            LocalDateTime startDate = (LocalDateTime) startDateField.get(object);
            LocalDateTime endDate = (LocalDateTime) endDateField.get(object);

            return startDate == null || endDate == null || startDate.isBefore(endDate);
        } catch (Exception e) {
            return true;
        }
    }
}
