package com.example.ex4.entity;

import jakarta.persistence.*;
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
    private double stars;

    @LastModifiedDate
    private LocalDateTime createdAt;
}
