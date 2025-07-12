package com.example.ex4.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    @NotNull
    private Long pkgOptionId;

    @NotNull
    private String subPkgName;

    @NotNull
    @DecimalMin("1.00")
    private BigDecimal monthlyCost;
}
