package com.example.ex4.service;

import com.example.ex4.entity.Review;
import com.example.ex4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews(Long providerProfileId) {
        return reviewRepository.findAllByProviderProfile_Id(providerProfileId);
    }

    public List<Review> getReviewsByProviderAndMinRating(Long providerProfileId, double minRating) {
        return reviewRepository.findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(providerProfileId, minRating);
    }


    public double getAverageStars(Long providerProfileId) {
        List<Review> reviews = reviewRepository.findAllByProviderProfile_Id(providerProfileId);

        if (reviews.isEmpty()) return 0.0;

        double starsAvg = reviews.stream()
                .mapToDouble(Review::getStars)
                .average()
                .orElse(0.0);
        double epsilon = 1e-6;
        double frac = starsAvg - Math.floor(starsAvg);

        if (frac < epsilon) {
            return Math.floor(starsAvg);
        } else {
            return Math.ceil(starsAvg * 2) / 2.0;
        }
    }

    public long getReviewersCount(Long providerProfileId) {

        return (long) reviewRepository.findAllByProviderProfile_Id(providerProfileId).size();
    }
}