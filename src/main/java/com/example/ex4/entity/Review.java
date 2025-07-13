package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity representing a user review for a provider.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Review implements Serializable {
    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The user who wrote the review.
     *
     * @see User
     */
    @ManyToOne
    private User user;

    /**
     * The reviewed provider profile.
     *
     * @see ProviderProfile
     */
    @ManyToOne
    private ProviderProfile providerProfile;

    /**
     * The text of the review.
     */
    @Column(nullable = false)
    private String reviewText;

    /**
     * Title or summary for the review.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Number of stars given (1-5).
     */
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Double stars;

    /**
     * Date and time when the review was last modified.
     */
    @LastModifiedDate
    private LocalDateTime createdAt;
}

