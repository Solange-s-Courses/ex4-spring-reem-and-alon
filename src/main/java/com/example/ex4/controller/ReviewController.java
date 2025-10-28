package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.SearchResultDTO;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;

/**
 * Controller for handling provider reviews.
 *
 * @see ReviewService
 * @see ProviderProfileRepository
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    /**
     * Repository for provider profiles.
     */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    /**
     * Service for review operations.
     */
    @Autowired
    private ReviewService reviewService;

    /**
     * Displays the list of reviews for a provider, with optional filtering by minimum rating.
     *
     * @param providerProfileId the ID of the provider
     * @param minRating minimum rating to filter reviews, or {@code null} for no filter
     * @param model Spring Model for adding attributes
     * @return the view name for the reviews list
     */
    @GetMapping
    public String getReviews(@RequestParam Long providerProfileId,
                             @RequestParam(required = false) Double minRating,
                             Model model) {

        ProviderProfile provider = providerProfileRepository.findById(providerProfileId).orElseThrow(() -> new RuntimeException("No profile found"));

        if (minRating != null && minRating > 0) {
            model.addAttribute("reviews", reviewService.getReviewsByProviderAndMinRating(providerProfileId, minRating));
        } else {
            model.addAttribute("reviews", reviewService.getAllReviews(providerProfileId));
        }

        model.addAttribute("provider", provider);
        model.addAttribute("currentMinRating", minRating);

        return "shared/reviews";
    }

    /**
     * Displays the add review form for a provider.
     *
     * @param providerProfileId the ID of the provider
     * @param model Spring Model for adding attributes
     * @return the view name for the add review form
     */
    @GetMapping("/add")
    public String getAddReviewForm(@RequestParam Long providerProfileId, Model model) {
        ProviderProfile provider = providerProfileRepository.findById(providerProfileId)
                .orElseThrow(() -> new RuntimeException("No profile found"));

        model.addAttribute("review", new Review());
        model.addAttribute("provider", provider);
        return "user/reviews-form";
    }

    /**
     * Processes the add review form and saves the review.
     *
     * @param userPrincipal the authenticated user
     * @param review the review details
     * @param bindingResult validation results
     * @param providerProfileId the provider ID
     * @param model Spring ModelMap for adding attributes
     * @return redirect to reviews list on success, or the add review form on error
     * @see ReviewService#saveReview(Review)
     */
    @PostMapping("/add")
    public ModelAndView addReview(@AuthenticationPrincipal MyUserPrincipal userPrincipal,
                                  @Valid @ModelAttribute Review review,
                                  BindingResult bindingResult,
                                  @RequestParam Long providerProfileId,
                                  @RequestParam String category,
                                  ModelMap model) {

        ProviderProfile provider = providerProfileRepository.findById(providerProfileId)
                .orElseThrow(() -> new RuntimeException("No profile found"));

        if (bindingResult.hasErrors()) {
            model.addAttribute("review", review);
            model.addAttribute("provider", provider);
            return new ModelAndView("user/reviews-form", model);
        }

        review.setProviderProfile(provider);
        review.setUser(userPrincipal.getUser());
        review.setCreatedAt(LocalDateTime.now());
        reviewService.saveReview(review);

        return new ModelAndView("redirect:/reviews?providerProfileId=" + providerProfileId +"&category="+category);
    }
}
