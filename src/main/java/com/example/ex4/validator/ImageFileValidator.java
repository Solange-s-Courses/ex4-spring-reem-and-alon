package com.example.ex4.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator to ensure a given file is a valid image (not null and not empty).
 * Used with {@link ValidImage}.
 */
public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    /**
     * Checks if the provided file is not null and not empty.
     *
     * @param value the file to validate
     * @param context validation context
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}

