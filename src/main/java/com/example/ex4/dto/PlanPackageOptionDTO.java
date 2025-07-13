package com.example.ex4.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for a package option (price for a given period and discount).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanPackageOptionDTO {

    /**
     * PlanPackageOption id
     */
    private Long id;

    /** Period display name */
    private String periodName;

    /** Duration in months */
    private int months;

    /** Discount (percentage) */
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    /** ID of the period (for DB lookup) */
    private Long periodId;
}
