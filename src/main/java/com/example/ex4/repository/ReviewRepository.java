package com.example.ex4.repository;

import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Review} entities.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds the average star rating for a given provider profile.
     *
     * @param providerProfile the provider profile
     * @return an optional with the average stars (may be empty if no reviews)
     */
    @Query("SELECT AVG(r.stars) FROM Review r WHERE r.providerProfile = :providerProfile")
    Optional<Double> findAvgStarsByProviderProfile(@Param("providerProfile") ProviderProfile providerProfile);

    /**
     * Finds all reviews for a given provider profile ID.
     *
     * @param providerProfileId the provider profile ID
     * @return list of reviews for the provider
     */
    List<Review> findAllByProviderProfile_Id(Long providerProfileId);

    /**
     * Finds all reviews for a provider and a specific rating, ordered by creation date (newest first).
     *
     * @param providerProfile_id the provider profile ID
     * @param stars the rating (number of stars)
     * @return list of matching reviews
     */
    List<Review> findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(long providerProfile_id, double stars);

    /**
     * Counts all reviews for a provider profile.
     *
     * @param providerProfile the provider profile
     * @return number of reviews for the profile
     */
    Long countAllByProviderProfile(ProviderProfile providerProfile);
}
