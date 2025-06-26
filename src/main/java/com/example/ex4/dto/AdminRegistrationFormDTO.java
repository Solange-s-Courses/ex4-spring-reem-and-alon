package com.example.ex4.dto;

import com.example.ex4.validator.ValidImage;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class AdminRegistrationFormDTO {
    @NotBlank(message = "userName is required")
    private String userName;

    @NotBlank(message = "mail is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Category is required")
    private String category;

    @ValidImage
    private MultipartFile imageFile;
}

