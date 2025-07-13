package com.example.ex4.entity;

import com.example.ex4.constants.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

/**
 * Entity representing a payment transaction for a subscription.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction implements Serializable {
    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The subscription this transaction is for.
     *
     * @see Subscription
     */
    @ManyToOne
    private Subscription subscription;

    /**
     * The amount charged.
     */
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal chargePrice;

    /**
     * Timestamp when the transaction occurred.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    /**
     * Transaction type (e.g. CHARGE, REFUND).
     *
     * @see com.example.ex4.constants.TransactionType
     */
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}

