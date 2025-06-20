package com.example.ex4.entity;

import com.example.ex4.constants.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subscription subscription;

    private BigDecimal amount;

    @LastModifiedDate
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
