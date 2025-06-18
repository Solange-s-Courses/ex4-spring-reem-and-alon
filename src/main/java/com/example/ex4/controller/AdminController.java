package com.example.ex4.controller;

import com.example.ex4.constants.ProviderType;
import com.example.ex4.constants.PlanPackageTypes;
import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.ProviderProfileService;
import com.example.ex4.service.UserService;
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

    @GetMapping
    public String adminIndex(Model model, Principal principal) {

        AppUser admin = adminService.findByUsername(principal.getName());
        ProviderProfile providerProfile = profileService.findProviderProfile(admin);
        List<PlanPackage> plans = planPackageService.getAllProviderPackages(providerProfile);

        model.addAttribute("admin", admin);
        model.addAttribute("profile", providerProfile);
        model.addAttribute("plans", plans);
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
        model.addAttribute("planPackage", new PlanPackage());
        model.addAttribute("planPackageTypes", PlanPackageTypes.values());
        return "admin/add-package-form";
    }

   @PostMapping("/add-package")
    public String addPackage(@Valid @ModelAttribute PlanPackage planPackage, BindingResult result, Principal principal, Model model) {
        AppUser admin = adminService.findByUsername(principal.getName());
        ProviderProfile providerProfile = profileService.findProviderProfile(admin);
        if (result.hasErrors()) {
            model.addAttribute("planPackageTypes", PlanPackageTypes.values());
            return "admin/add-package-form";
        }
        planPackageService.saveNewPackage(providerProfile,planPackage);
        return "redirect:/admin";
    }
}
