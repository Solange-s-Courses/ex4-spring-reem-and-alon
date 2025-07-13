package com.example.ex4.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing aggregated search results for a provider.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDTO {
    /**
     * Average review stars for the provider.
     */
    private double avgStars;

    /**
     * Number of reviews for the provider.
     */
    private long reviewCount;

    /**
     * Provider display name.
     */
    private String providerName;

    /**
     * Plans/packages offered by this provider.
     */
    @Builder.Default
    List<PlanPackageDTO> plans = new ArrayList<>();
}
