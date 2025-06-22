package com.example.ex4.controller;

import com.example.ex4.components.UserHolder;
import com.example.ex4.constants.ProviderType;
import com.example.ex4.constants.PlanPackageTypes;
import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Transaction;
import com.example.ex4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService adminService;
    @Autowired
    private ProviderProfileService profileService;
    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private TransactionService transactionService;



    @Autowired
    private UserHolder userHolder;

    @GetMapping
    public String adminIndex(Model model, Principal principal) {
        System.out.println(principal.getName());
        AppUser admin = adminService.findByUsername(principal.getName());
        ProviderProfile providerProfile = profileService.findProviderProfile(admin);
        List<PlanPackage> plans = planPackageService.getAllProviderPackages(providerProfile);
        List<Transaction> transactions = transactionService.findAllProviderTransactions(plans);

        model.addAttribute("admin", admin);
        model.addAttribute("profile", providerProfile);
        model.addAttribute("plans", plans);
        model.addAttribute("transactions", transactions);
        return "admin/index";
    }

/*    @GetMapping("/add-profile")
    public String adminProfile(Model model) {
        model.addAttribute("profile", new AdminRegistrationFormDTO());
        model.addAttribute("providers", ProviderType.values());
        return "admin/add-profile-form";
    }

    @PostMapping("/add-profile")
    public String createProfile(@Valid @ModelAttribute("profile") AdminRegistrationFormDTO profile,
                                BindingResult result, Principal principal, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("providers", ProviderType.values());
            return "admin/add-profile-form";
        }
        AppUser admin = adminService.findByUsername(principal.getName());
        profileService.saveProviderProfile(admin, profile);
        return "redirect:/admin";
    }*/

    @GetMapping("/add-package")
    public String getPackageForm(Model model) {
        model.addAttribute("planPackage", new PlanPackageDTO());
        model.addAttribute("planPackageTypes", PlanPackageTypes.values());
        return "admin/add-package-form";
    }

    @PostMapping("/add-package")
    public String addPackage(
            @Valid @ModelAttribute("planPackageDTO") PlanPackageDTO planPackageDTO,
            BindingResult result, Model model)
    {
        ProviderProfile providerProfile = profileService.findProviderProfile(userHolder.getUser());
        if (result.hasErrors()) {
            model.addAttribute("planPackageTypes", PlanPackageTypes.values());
            return "admin/add-package-form";
        }
        planPackageService.saveNewPackage(providerProfile, planPackageDTO);
        return "redirect:/admin";
    }
}
