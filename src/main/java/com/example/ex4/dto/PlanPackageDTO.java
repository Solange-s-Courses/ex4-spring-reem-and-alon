package com.example.ex4.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for a PlanPackage.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanPackageDTO {
    /** Title of the package */
    @NotBlank(message = "title is required")
    private String title;

    /** Description of the package */
    @NotBlank(message = "description is required")
    private String description;

    /** List of package options (for each period) */
    @Builder.Default
    private List<PlanPackageOptionDTO> planOptions = new ArrayList<>();

    /** Base monthly cost */
    @NotNull(message = "monthly cost is required")
    @DecimalMin(value = "1", message = "monthly price cost must be positive")
    @DecimalMax(value = "3000", message = "monthly price cannot be more than 3000")
    private BigDecimal monthlyCost;

    /** Expiry date of the package */
    @NotNull(message = "expired date is required")
    private LocalDate expiryDate;

    /** Provider profile ID */
    @NotNull(message = "provider profile Id is required")
    private Long providerProfileId;

    @Override
    public String toString() {
        return "PlanPackageDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", planOptions=" + planOptions +
                ", monthlyCost=" + monthlyCost +
                ", expiryDate=" + expiryDate +
                ", providerProfileId=" + providerProfileId +
                '}';
    }
}
