package com.example.ex4.controller;


import com.example.ex4.constants.ProviderType;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/search-providers")
public class SearchProviderController {

    @Autowired
    PlanPackageService planPackageService;

    @GetMapping
    public String searchProvidersPage(Model model) {
        model.addAttribute("providers", ProviderType.values());
        return "user/search-providers";
    }

    @PostMapping
    public String searchService(@RequestParam String providerCategory, Model model) {
        model.addAttribute("providers", ProviderType.values());
        List<PlanPackage> results= planPackageService.getAllPackagesByCategory(providerCategory);
        model.addAttribute("results", results);
        return "user/search-providers";
    }
}
