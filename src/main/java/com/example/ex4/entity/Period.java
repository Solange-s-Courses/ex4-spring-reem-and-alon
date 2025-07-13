package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * Entity representing a subscription period (e.g. monthly, yearly).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Period implements Serializable {
    /**
     * Unique identifier for the period.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the period (e.g. "Yearly", "Monthly").
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Duration of the period in months.
     */
    @Column(nullable = false, unique = true)
    private int months;
}
