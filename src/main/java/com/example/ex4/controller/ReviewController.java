package com.example.ex4.controller;

import com.example.ex4.dto.ReviewDTO;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("reviews",reviewService.getAllReviews(providerProfile));
        model.addAttribute("provider",providerProfile);
        model.addAttribute("reviewFields",new ReviewDTO());
        return "shared/reviews";
    }
}
