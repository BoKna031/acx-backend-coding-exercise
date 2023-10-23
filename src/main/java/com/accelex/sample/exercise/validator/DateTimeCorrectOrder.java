package com.accelex.sample.exercise.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeCorrectOrderValidator.class)
public @interface DateTimeCorrectOrder {
    String message() default "Date order is not valid";

    String startDate();
    String endDate();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
