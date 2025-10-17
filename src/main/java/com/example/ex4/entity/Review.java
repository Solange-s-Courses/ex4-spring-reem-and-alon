package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Please enter description of the review.")
    private String reviewText;

    /**
     * Title or summary for the review.
     */
    @NotBlank(message = "Please enter title to your review.")
    private String title;

    /**
     * Number of stars given (1-5).
     */
    @NotNull(message = "Please select a star rating between 1 and 5.")
    @DecimalMin(value = "1.0", message = "Rating must be at least 1 star.")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5 stars.")
    private Double stars;

    /**
     * Date and time when the review was last modified.
     */
    @LastModifiedDate
    private LocalDateTime createdAt;
}

