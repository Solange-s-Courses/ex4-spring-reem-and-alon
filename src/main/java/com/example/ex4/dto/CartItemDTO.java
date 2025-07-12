package com.example.ex4.dto;

import com.example.ex4.validator.ValidSubscriptionPeriodLabel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    @NotNull
    private Long pkgId;

    @NotNull
    @ValidSubscriptionPeriodLabel
    private String subPkgName;

    @NotNull
    @Min(1)
    private Double monthlyCost;
}
