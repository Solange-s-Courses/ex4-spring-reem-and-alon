package com.example.ex4.dto;

import com.example.ex4.entity.PlanPackage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {
    List<PlanPackage> planPackages;
    private long reviewCount;
    private double avgStars;
    private boolean avgHasHalfStarOrMore;
}
