package com.example.ex4.controller;

import com.example.ex4.dto.ReviewDTO;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{providerProfileId}")
    public String getReviews(@PathVariable Long providerProfileId, Model model) {
        ProviderProfile providerProfile=providerProfileRepository.findById(providerProfileId).orElse(null);
        ReviewDTO reviewFields=new ReviewDTO();
        reviewFields.setProviderId(providerProfileId);
        model.addAttribute("reviews",reviewService.getAllReviews(providerProfile));
        model.addAttribute("provider",providerProfile);
        model.addAttribute("reviewFields",reviewFields);
        return "shared/reviews";
    }

    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute("reviewFields") ReviewDTO reviewFields,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        ProviderProfile provider = providerProfileRepository.findById(reviewFields.getProviderId()).orElse(null);
        if (bindingResult.hasErrors()) {
            model.addAttribute("reviews", reviewService.getAllReviews(provider));
            model.addAttribute("provider", provider);
            return "shared/review-form";
        }
        try {
            reviewService.saveReview(reviewFields,provider);
            redirectAttributes.addFlashAttribute("successMessage", "Review saved successfully!");
            return "redirect:/reviews/" + reviewFields.getProviderId();

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("reviews", reviewService.getAllReviews(provider));
            model.addAttribute("provider", provider);
            return "redirect:/reviews/" + reviewFields.getProviderId();
        }
    }
}