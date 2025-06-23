package com.example.ex4.service;

import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import com.example.ex4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews(ProviderProfile providerProfile) {
        return reviewRepository.findReviewsByProviderProfile(providerProfile);
    }

/*    public List<Review> getReviewsSince(Long serviceId, Long lastId) {
        return reviewRepository.findByServiceIdAndIdGreaterThan(serviceId, lastId);
    }

    public double getAverageStars(Long serviceId) {
        Double avg = reviewRepository.findAverageStarsByServiceId(serviceId);
        return avg != null ? avg : 0.0;
    }

    public long getReviewersCount(Long serviceId) {
        Long cnt = reviewRepository.countByServiceId(serviceId);
        return cnt != null ? cnt : 0;
    }*/
}