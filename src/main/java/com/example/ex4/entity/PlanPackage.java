package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a plan/package offered by a provider.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackage implements Serializable {

    /**
     * Unique identifier for the plan package.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Title of the plan/package.
     */
    private String title;

    /**
     * Description of the plan/package.
     */
    private String description;

    /**
     * Base monthly cost for the package.
     */
    private BigDecimal monthlyCost;

    /**
     * Expiry date of the package.
     */
    private LocalDate expiryDate;

    /**
     * The provider profile who owns this package.
     *
     * @see ProviderProfile
     */
    @ManyToOne
    private ProviderProfile providerProfile;

    /**
     * List of package options (for different periods, discounts).
     *
     * @see PlanPackageOption
     */
    @OneToMany(mappedBy = "planPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlanPackageOption> planOptions = new ArrayList<>();
}
