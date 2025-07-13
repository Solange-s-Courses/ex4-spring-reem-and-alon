package com.example.ex4.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

/**
 * DTO representing a cart item (package option selection).
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    /**
     * Selected package option ID.
     */
    @NotNull
    private Long pkgOptionId;

    /**
     * Package ID.
     */
    @NotNull
    private Long pkgId;

    /**
     * Name of the package option/period.
     */
    @NotNull
    private String subPkgName;

    /**
     * Monthly cost for this cart item.
     */
    @NotNull
    @DecimalMin("1.00")
    private BigDecimal monthlyCost;
}
