package com.example.ex4.controller;


import com.example.ex4.components.ShoppingCart;
import com.example.ex4.constants.ProviderType;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/search-providers")
public class SearchProviderController {

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @GetMapping
    public String searchProvidersPage(Model model) {
        model.addAttribute("providers", ProviderType.values());
        model.addAttribute("shoppingCart", sessionCart.getProducts());
        return "user/search-providers";
    }

    @PostMapping
    public String searchService(@RequestParam String providerCategory, RedirectAttributes redirectAttributes) {
        System.out.println(providerCategory);
        List<PlanPackage> results = planPackageService.getAllPackagesByCategory(providerCategory);
        redirectAttributes.addFlashAttribute("results", results);
        return "redirect:/user/search-providers";
    }
}
