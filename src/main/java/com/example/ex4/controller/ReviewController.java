/*
package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ProviderProfileService;
import com.example.ex4.service.ReviewLongPollingService;
import com.example.ex4.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewLongPollingService reviewLongPollingService;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    // הוספת ביקורת חדשה
    @PostMapping("/")
    public Review addReview(@RequestBody Review review) {
        Review saved = reviewService.saveReview(review);

        List<Review> newReviews = List.of(saved);
        //deferredResultService.notifyClients(review.getServiceId(), newReviews);

        return saved;
    }

    @GetMapping("/{providerProfileId}")
    public List<Review> getReviews(@PathVariable Long providerProfileId) {
        ProviderProfile providerProfile=providerProfileRepository.findById(providerProfileId).orElse(null);
        return reviewService.getAllReviews(providerProfile);
    }

    @GetMapping("/long-poll")
    public DeferredResult<List<Review>> longPoll(
            @RequestParam Long serviceId,
            @RequestParam(defaultValue = "0") Long lastReviewId) {

        List<Review> newReviews = reviewService.getReviewsSince(serviceId, lastReviewId);
        DeferredResult<List<Review>> deferred = new DeferredResult<>(15000L, Collections.emptyList());

        if (!newReviews.isEmpty()) {
            deferred.setResult(newReviews);
            return deferred;
        }

        deferredResultService.addWaiter(serviceId, deferred);
        return deferred;
    }

    // ממוצע דירוגים ומספר מדרגים
*/
/*    @GetMapping("/summary/{serviceId}")
    public ReviewSummaryDTO getSummary(@PathVariable Long serviceId) {
        double avg = reviewService.getAverageStars(serviceId);
        long count = reviewService.getReviewersCount(serviceId);
        return new ReviewSummaryDTO(serviceId, avg, count);
    }*//*

}*/
