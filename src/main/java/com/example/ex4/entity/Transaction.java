package com.example.ex4.entity;

import com.example.ex4.constants.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Min(1)
    private int amount;

    @LastModifiedDate
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
