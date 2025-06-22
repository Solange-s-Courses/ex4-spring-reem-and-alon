package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
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

    private String title;

    private String description;

    private String packageType;

    private int monthlyCost;

    private LocalDate expiryDate;

    @ManyToOne
    private ProviderProfile providerProfile;
}
