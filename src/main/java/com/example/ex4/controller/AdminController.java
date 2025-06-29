package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.constants.SubscriptionPeriod;
import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Transaction;
import com.example.ex4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProviderProfileService profileService;
    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String adminIndex(@AuthenticationPrincipal MyUserPrincipal userPrincipal,Model model) {
        User admin = userPrincipal.getUser();
        ProviderProfile providerProfile = profileService.findProviderProfile(admin);
        List<PlanPackage> plans = planPackageService.getAllProviderPackages(providerProfile);
        List<Transaction> transactions = transactionService.findAllProviderTransactions(plans);

        model.addAttribute("admin", admin);
        model.addAttribute("profile", providerProfile);
        model.addAttribute("plans", plans);
        model.addAttribute("transactions", transactions);
        return "admin/index";
    }

    @GetMapping("/add-package")
    public String getPackageForm(Model model) {
        model.addAttribute("planPackage", new PlanPackageDTO());
        model.addAttribute("subscriptionPeriod", SubscriptionPeriod.values());
        return "admin/add-package-form";
    }

    @PostMapping("/add-package")
    public String addPackage(
            @AuthenticationPrincipal MyUserPrincipal userPrincipal,
            @Valid @ModelAttribute("planPackageDTO") PlanPackageDTO planPackageDTO,
            BindingResult result, Model model)
    {
        ProviderProfile providerProfile = profileService.findProviderProfile(userPrincipal.getUser());
        if (result.hasErrors()) {
            model.addAttribute("subscriptionPeriod", SubscriptionPeriod.values());
            return "admin/add-package-form";
        }
        planPackageService.saveNewPackage(providerProfile, planPackageDTO);
        return "redirect:/admin";
    }
}
