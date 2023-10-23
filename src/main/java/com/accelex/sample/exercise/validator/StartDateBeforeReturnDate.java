package com.accelex.sample.exercise.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateBeforeReturnDateValidator.class)
public @interface StartDateBeforeReturnDate {
    String message() default "Start date must be before return date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
