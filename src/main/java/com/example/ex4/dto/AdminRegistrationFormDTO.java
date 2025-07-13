package com.example.ex4.dto;

import com.example.ex4.validator.ValidImage;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for admin/provider registration form.
 */
@NoArgsConstructor
@Data
public class AdminRegistrationFormDTO {
    /**
     * Admin/provider username.
     */
    @NotBlank(message = "userName is required")
    private String userName;

    /**
     * Admin/provider email.
     */
    @NotBlank(message = "mail is required")
    private String email;

    /**
     * Registration password.
     */
    @NotBlank(message = "password is required")
    private String password;

    /**
     * Company name of provider.
     */
    @NotBlank(message = "Company name is required")
    private String companyName;

    /**
     * Provider category.
     */
    @NotBlank(message = "Category is required")
    private String category;

    /**
     * Profile image file.
     * {@link ValidImage}
     */
    @ValidImage
    private MultipartFile imageFile;
}
