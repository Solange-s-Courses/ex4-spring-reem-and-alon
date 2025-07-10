package com.example.ex4.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {
    String message() default "Image file required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

