package com.example.ex4.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for user subscription information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubscriptionDTO {
    /**
     * Status of the subscription (e.g. ACTIVE).
     */
    private String status;

    /**
     * Start date of the subscription.
     */
    private LocalDate startDate;

    /**
     * Plan/package description.
     */
    private String planDescription;

    /**
     * Plan/package name.
     */
    private String planName;

    /**
     * Monthly cost for the subscription.
     */
    private BigDecimal monthlyCost;

    /**
     * Provider user/profile ID.
     */
    private Long providerId;

    /**
     * Provider name.
     */
    private String providerName;

    /**
     * Provider category.
     */
    private String category;
}
