package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;

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
//
//    @Transient
//    @OneToMany(mappedBy = "subscription")
//    private List<Transaction> transactions;

    @ManyToOne
    private AppUser appUser;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Builder.Default
    private String status = "ACTIVE";
}
