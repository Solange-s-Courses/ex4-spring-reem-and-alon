package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ProviderProfile providerProfile;

    @Column(nullable = false)
    private String reviewText;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private double stars;

    @LastModifiedDate
    private LocalDateTime createdAt;
}
