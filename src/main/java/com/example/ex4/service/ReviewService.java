package com.example.ex4.service;

import com.example.ex4.entity.Review;
import com.example.ex4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for saving and retrieving user reviews for provider profiles.
 *
 * @see Review
 * @see ReviewRepository
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Saves a review to the repository.
     *
     * @param review the review entity to save
     */
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /**
     * Retrieves all reviews for a specific provider profile.
     *
     * @param providerProfileId the provider profile ID
     * @return list of reviews for the provider
     */
    public List<Review> getAllReviews(Long providerProfileId) {
        return reviewRepository.findAllByProviderProfile_Id(providerProfileId);
    }

    /**
     * Retrieves all reviews for a provider profile with a minimum rating,
     * ordered by creation date (newest first).
     *
     * @param providerProfileId the provider profile ID
     * @param minRating minimum number of stars
     * @return list of reviews meeting the criteria
     */
    public List<Review> getReviewsByProviderAndMinRating(Long providerProfileId, double minRating) {
        return reviewRepository.findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(providerProfileId, minRating);
    }
}
