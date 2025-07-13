package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity representing a specific option for a plan package (period, discount, price).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackageOption implements Serializable {
    /**
     * Unique identifier for the option.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Period for this package option (e.g. "Yearly", "Monthly").
     *
     * @see Period
     */
    @ManyToOne(optional = false)
    private Period period;

    /**
     * The parent plan package.
     *
     * @see PlanPackage
     */
    @ManyToOne(optional = false)
    private PlanPackage planPackage;

    /**
     * Discount for this option (percentage).
     */
    @Builder.Default
    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    /**
     * Price for this option (possibly after discount).
     */
    @Transient
    private BigDecimal optionPrice;
}

