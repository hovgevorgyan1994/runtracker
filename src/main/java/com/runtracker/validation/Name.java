package com.runtracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Size(min = 2, max = 20, message = "The length should be between 2 and 20 characters!")
@Pattern(regexp = "^[A-Za-z]*$", message = "Should contain only letters!")
@Documented
@Constraint(validatedBy = {})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

  String message() default "Invalid name!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}