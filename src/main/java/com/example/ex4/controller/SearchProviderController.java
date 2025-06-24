package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.constants.ProviderType;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
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
    private ShoppingCart sessionCart;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;

    @GetMapping
    public String searchProvidersPage(
            @RequestParam(value = "providerCategory", required = false) String providerCategory,
            Model model) {
        model.addAttribute("providers", ProviderType.values());
        model.addAttribute("shoppingCart", sessionCart.getProducts());
        model.addAttribute("selectedCategory", providerCategory);

        if (providerCategory != null && !providerCategory.isBlank()) {
            List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);
            Map<Long, List<PlanPackage>> groupedResult = allPackages.stream()
                    .collect(Collectors.groupingBy(
                            planPackage -> planPackage.getProviderProfile().getId(),
                            LinkedHashMap::new,
                            Collectors.toList()));
            model.addAttribute("groupedResult", groupedResult);
        } else {
            model.addAttribute("groupedResult", Collections.emptyMap());
        }

        return "user/search-providers";
    }



    @PostMapping
    public String searchProviders(@RequestParam String providerCategory, RedirectAttributes redirectAttributes) {
        List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);
        Map<Long, List<PlanPackage>> groupedResult = allPackages.stream()
                        .collect(Collectors.groupingBy(planPackage -> planPackage.getProviderProfile().getId(), LinkedHashMap::new, Collectors.toList()));

        redirectAttributes.addFlashAttribute("groupedResult", groupedResult);
        redirectAttributes.addFlashAttribute("selectedCategory", providerCategory);
        return "redirect:/user/search-providers";
    }
}
