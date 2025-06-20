package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message= "this field is required")
    private String packageType;

    @NotBlank(message = "monthly price cost is required")
    @DecimalMin("1")
    private BigDecimal monthlyCost;

    @NotBlank(message = "expired date is required")
    private LocalDate expiryDate;

    @ManyToOne
    private ProviderProfile providerProfile;
}
