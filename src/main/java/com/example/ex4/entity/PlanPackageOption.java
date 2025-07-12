package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity for a plan package option (per period).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlanPackageOption implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Period period;

    @ManyToOne(optional = false)
    private PlanPackage planPackage;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    private BigDecimal optionPrice;
}
