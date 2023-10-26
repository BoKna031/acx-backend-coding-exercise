package com.accelex.sample.exercise.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RentalStatusValidator.class)
public @interface ValidRentalStatus {

    String message() default "Rental status is not valid";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
