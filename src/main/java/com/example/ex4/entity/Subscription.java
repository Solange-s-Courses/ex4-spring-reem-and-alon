package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity representing a user's subscription to a plan package option.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Subscription implements Serializable {
    /**
     * Unique identifier for the subscription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The subscribing user.
     *
     * @see User
     */
    @ManyToOne
    private User user;

    /**
     * Subscription start date.
     */
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    /**
     * The plan package option for this subscription.
     *
     * @see PlanPackageOption
     */
    @ManyToOne
    private PlanPackageOption planPackageOption;

    /**
     * Payment transactions associated with this subscription.
     *
     * @see Transaction
     */
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> paymentTransactions;

    /**
     * Status of the subscription (default "ACTIVE").
     */
    @Builder.Default
    private String status = "ACTIVE";
}

