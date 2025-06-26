package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.ProviderCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView initSearchFormData(@RequestParam(value = "category", required = false) String category,
                                           HttpServletRequest request, ModelMap model) {
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        model.addAttribute("shoppingCart", sessionCart.getProducts());

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
        System.out.println(providerCategory);
        List<PlanPackage> allPackages = planPackageService.getAllPackagesByCategory(providerCategory);
        System.out.println(allPackages);
        Map<Long, List<PlanPackage>> groupedResult = allPackages.stream()
                .collect(Collectors.groupingBy(planPackage -> planPackage.getProviderProfile().getId(),
                        LinkedHashMap::new, Collectors.toList()));

        System.out.println(groupedResult);

        model.addAttribute("groupedResult", groupedResult);
        return "user/search-providers";
    }
}
