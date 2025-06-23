package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private PlanPackage planPackage;

    @ManyToOne
    private User user;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Builder.Default
    private String status = "ACTIVE";
}
