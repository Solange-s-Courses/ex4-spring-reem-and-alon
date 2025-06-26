package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.ProviderCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/search-providers")
public class SearchProviderController {

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private ProviderCategoryService providerCategoryService;

    @Autowired
    private ShoppingCart sessionCart;;

    @GetMapping
    public String initSearchFormData(@RequestParam(value = "category", required = false) String category, Model model) {
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        model.addAttribute("shoppingCart", sessionCart.getProducts());
        model.addAttribute("selectedCategory", category);

        if (category != null && !category.isBlank()) {
           return "forward:/user/search-providers/result";
        }

        model.addAttribute("groupedResult", Collections.emptyMap());
        return "user/search-providers";
    }

    @GetMapping("/result")
    public String groupedByProviderResult(Model model) {
        String category = (String)model.getAttribute("selectedCategory");

        ProviderCategory providerCategory = providerCategoryService.findByName(category);
        List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);

        Map<Long, List<PlanPackage>> groupedResult = allPackages.stream()
                .collect(Collectors.groupingBy(planPackage -> planPackage.getProviderProfile().getId(),
                        LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("groupedResult", groupedResult);
        return "user/search-providers";
    }
}
