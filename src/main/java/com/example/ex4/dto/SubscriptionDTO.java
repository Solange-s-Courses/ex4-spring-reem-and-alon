package com.example.ex4.dto;

import lombok.*;

import java.awt.image.PixelGrabber;
import java.math.BigDecimal;
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
    private BigDecimal monthlyCost;

    private Long providerId;
    private String providerName;

}

