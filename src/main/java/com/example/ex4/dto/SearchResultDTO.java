package com.example.ex4.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDTO {
    private double avgStars;
    private long reviewCount;
    private String providerName;

    @Builder.Default
    List<PlanPackageDTO> plans = new ArrayList<>();
}
