package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String getReviews(@RequestParam Long providerProfileId,
                             @RequestParam(required = false) Double minRating,
                             Model model) {

        ProviderProfile provider = providerProfileRepository.findById(providerProfileId)
                .orElseThrow(() -> new RuntimeException("No profile found"));

        if (minRating != null && minRating > 0) {
            model.addAttribute("reviews", reviewService.getReviewsByProviderAndMinRating(providerProfileId, minRating));
        } else {
            model.addAttribute("reviews", reviewService.getAllReviews(providerProfileId));
        }

        model.addAttribute("provider", provider);
        model.addAttribute("currentMinRating", minRating);

        return "shared/reviews";
    }

    @GetMapping("/add")
    public String getAddReviewForm(@RequestParam Long providerProfileId, Model model) {
        ProviderProfile provider = providerProfileRepository.findById(providerProfileId)
                .orElseThrow(() -> new RuntimeException("No profile found"));

        model.addAttribute("review", new Review());
        model.addAttribute("provider", provider);
        return "user/reviews-form";
    }

    @PostMapping("/add")
    public ModelAndView addReview(@AuthenticationPrincipal MyUserPrincipal userPrincipal,
                                  @Valid @ModelAttribute Review review,
                                  BindingResult bindingResult,
                                  @RequestParam Long providerProfileId) {

        ProviderProfile provider = providerProfileRepository.findById(providerProfileId)
                .orElseThrow(() -> new RuntimeException("No profile found"));

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("user/reviews-form");
            modelAndView.addObject("review", review);
            modelAndView.addObject("provider", provider);
            return modelAndView;
        }

        review.setProviderProfile(provider);
        review.setUser(userPrincipal.getUser());
        review.setCreatedAt(LocalDateTime.now());
        reviewService.saveReview(review);

        return new ModelAndView("redirect:/reviews/" + providerProfileId);
    }
}