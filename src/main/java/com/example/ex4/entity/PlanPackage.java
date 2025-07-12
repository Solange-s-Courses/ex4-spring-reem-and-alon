package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for a plan package.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private BigDecimal monthlyCost;
    private LocalDate expiryDate;

    @ManyToOne
    private ProviderProfile providerProfile;

    @OneToMany(mappedBy = "planPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlanPackageOption> planOptions = new ArrayList<>();
}
