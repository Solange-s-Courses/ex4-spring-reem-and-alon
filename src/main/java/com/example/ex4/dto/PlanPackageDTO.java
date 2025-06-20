package com.example.ex4.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanPackageDTO {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "this field is required")
    private String packageType;

    @Min(value = 1, message = "monthly price cost must be positive")
    private int monthlyCost;

    @NotNull(message = "expired date is required")
    private LocalDate expiryDate;

    private long providerProfileId;
}

