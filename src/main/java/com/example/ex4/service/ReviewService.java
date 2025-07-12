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
}