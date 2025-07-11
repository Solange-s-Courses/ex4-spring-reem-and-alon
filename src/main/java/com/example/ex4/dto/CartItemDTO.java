package com.example.ex4.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Long pkgId;
    private String subPkgName;
    private Integer price;
}
