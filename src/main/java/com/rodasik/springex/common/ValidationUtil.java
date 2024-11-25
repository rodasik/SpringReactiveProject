package com.rodasik.springex.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public class ValidationUtil {
    public static <T> void validate(T object, Validator validator) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            String errors = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("Validation failed");
            throw new IllegalArgumentException(errors);
        }
    }
}
