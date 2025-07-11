package com.example.ex4.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long pkgId;
    private String subPkgName;
    private Double monthlyCost;
}
