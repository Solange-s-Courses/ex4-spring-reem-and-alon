package com.example.ex4.controller;

import com.example.ex4.components.RequestUrlHolder;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.ProviderCategoryService;
import com.example.ex4.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/search-providers")
public class SearchProviderController {

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private ProviderCategoryService providerCategoryService;

    @Autowired
    private ShoppingCart sessionCart;

/*    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RequestUrlHolder requestUrlHolder;*/

    @GetMapping
    public ModelAndView initSearchFormData(@RequestParam(value = "category", required = false) String category,
                                           HttpServletRequest request, ModelMap model) {
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        model.addAttribute("cartItems", sessionCart.getItems());

        if (category != null && !category.isBlank()) {
            request.setAttribute("category", category);
            model.addAttribute("selectedCategory", category);
            return new ModelAndView("forward:/user/search-providers/result", model);
        }

        model.addAttribute("groupedResult", Collections.emptyMap());
        return new ModelAndView("user/search-providers", model);
    }

    @GetMapping("/result")
    public String groupedByProviderResult(Model model, HttpServletRequest request) {
        String category = (String)request.getAttribute("category");
        ProviderCategory providerCategory = providerCategoryService.findByName(category);
        List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);

        Map<Long, List<PlanPackage>> groupedResult = planPackageService.groupPackagesWithReviewStats(allPackages);

        model.addAttribute("groupedResult", groupedResult);
        return "user/search-providers";
    }
}
