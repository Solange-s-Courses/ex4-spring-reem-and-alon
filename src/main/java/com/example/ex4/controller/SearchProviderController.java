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
import java.util.List;
import java.util.Set;
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
    public String searchProvidersPage(Model model) {
        model.addAttribute("providers", ProviderType.values());
        model.addAttribute("shoppingCart", sessionCart.getProducts());
        return "user/search-providers";
    }

    @PostMapping
    public String searchProviders(@RequestParam String providerCategory, RedirectAttributes redirectAttributes) {
        List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);
        Set<Long> packageIds = allPackages.stream().map(PlanPackage::getId).collect(Collectors.toSet());

        List<PlanPackage> results = allPackages.stream()
                .filter(planPackage -> packageIds.contains(planPackage.getId()))
                .toList();

        redirectAttributes.addFlashAttribute("results", results);
        redirectAttributes.addFlashAttribute("selectedCategory", providerCategory);
        return "redirect:/user/search-providers";
    }
}
