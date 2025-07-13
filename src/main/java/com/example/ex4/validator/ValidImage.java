package com.example.ex4.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation to validate that a file upload field is a non-empty image.
 * Usage: annotate a {@code MultipartFile} field.
 *
 * @see ImageFileValidator
 */
@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {

    /**
     * Default validation error message.
     */
    String message() default "Image file required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

