package com.example.ex4.dto;

import lombok.*;
import java.math.BigDecimal;

/**
 * DTO for a package option (price for a given period & discount).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanPackageOptionDTO {

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

    @Override
    public String toString() {
        return "PlanPackageOptionDTO{" +
                "periodName='" + periodName + '\'' +
                ", months=" + months +
                ", discount=" + discount +
                ", periodId=" + periodId +
                '}';
    }
}
