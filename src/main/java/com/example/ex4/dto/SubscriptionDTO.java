package com.example.ex4.dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubscriptionDTO {
    private String status;
    private LocalDate startDate;

    private String planDescription;
    private String planName;
    private double monthlyCost;

    private Long providerId;
    private String providerName;
    private String category;

}

