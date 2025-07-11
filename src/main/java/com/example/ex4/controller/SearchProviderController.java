package com.example.ex4.controller;

import com.example.ex4.components.SearchCategoryHolder;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.dto.SearchResultDTO;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.ProviderCategoryService;
import com.example.ex4.service.ProviderProfileService;
import com.example.ex4.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
@RequestMapping("/user/search-providers")
public class SearchProviderController {


    @Autowired
    private ProviderCategoryService providerCategoryService;

    @Autowired
    private ShoppingCart sessionCart;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SearchCategoryHolder searchCategoryHolder;
    @Autowired
    private ProviderProfileService providerProfileService;

    @GetMapping
    public ModelAndView initSearchFormData(@RequestParam(value = "category", required = false) String category,
                                           ModelMap model) {
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        model.addAttribute("cartItems", sessionCart.getItems());

        if (category != null && !category.isBlank()) {
            searchCategoryHolder.setCategory(category);
            model.addAttribute("selectedCategory", category);
            return new ModelAndView("forward:/user/search-providers/result", model);
        }

        model.addAttribute("groupedResult", Collections.emptyMap());
        return new ModelAndView("user/search-providers", model);
    }

    @GetMapping("/result")
    public ModelAndView groupedByProviderResult(ModelMap model) {
        String category = searchCategoryHolder.getCategory();
        ProviderCategory providerCategory = providerCategoryService.findByName(category);

        List<ProviderProfile> providers = providerCategory.getProviders().stream().
                filter(profile -> profile.getPlanPackage() != null && !profile.getPlanPackage().isEmpty())
                .toList();
        List<SearchResultDTO> results = new ArrayList<>();
        providers.forEach(provider -> {
            SearchResultDTO searchResultDTO = new SearchResultDTO();
            searchResultDTO.setPlanPackages(provider.getPlanPackage());
            searchResultDTO.setAvgStars(reviewService.getAverageStars(provider));
            searchResultDTO.setReviewCount(reviewService.getReviewersCount(provider));
            results.add(searchResultDTO);
        });

        model.addAttribute("results", results);
        return new ModelAndView("user/search-providers",model);
    }
}
