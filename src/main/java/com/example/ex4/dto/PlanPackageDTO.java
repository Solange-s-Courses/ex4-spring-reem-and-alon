package com.example.ex4.dto;

import com.example.ex4.constants.SubscriptionPeriod;
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

    @Builder.Default
    private Double yearlyDiscount = 0.0;

    @Builder.Default
    private Double halfYearlyDiscount = 0.0;

    @Builder.Default
    private Double threeMonthDiscount = 0.0;

    @Min(value = 1, message = "monthly price cost must be positive")
    @Max(value = 3000, message = "monthly price cannot be more than 3000")
    private int monthlyCost;

    @NotNull(message = "expired date is required")
    private LocalDate expiryDate;

    private long providerProfileId;
}

