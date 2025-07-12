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

/**
 * Controller for handling provider (admin) endpoint requests.
 * <p>
 * Responsible for managing provider profiles, packages, and related transactions.
 * Provides endpoints for the admin dashboard, package creation, and related operations.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Service for business logic of {@link ProviderProfile}.
     */
    @Autowired
    private ProviderProfileService profileService;

    /**
     * Service for business logic of {@link PlanPackage}.
     */
    @Autowired
    private PlanPackageService planPackageService;

    /**
     * Service for business logic of {@link Transaction}.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Displays the admin dashboard with the admins profile, packages, and transactions.
     *
     * @param userPrincipal the authenticated admin user
     * @param model transfer the data to the view
     * @return the admin dashboard page (his index page)
     * @see MyUserPrincipal
     */
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

    /**
     * render the "add plan form" page
     *
     * @param model transfer the data to the view
     * @return add package form page
     */
    @GetMapping("/add-package")
    public String getPackageForm(Model model) {
        model.addAttribute("planPackage", new PlanPackageDTO());
        model.addAttribute("subscriptionPeriod", SubscriptionPeriod.values());
        return "admin/add-package-form";
    }

    /**
     * This method handle the submission of the "add package" form.
     * Validate the input and if all inputs are valid it creates new {@link PlanPackage}
     * and save it into the database
     *
     * @param userPrincipal the authenticated admin user
     * @param planPackageDTO the {@code PlanPackage} form inputs
     * @param result contains the validation errors of the {@code planPackageDTO}
     * @param model transfer the data to the view
     * @return redirect to admin dashboard page
     */
    @PostMapping("/add-package")
    public String addPackage(
            @AuthenticationPrincipal MyUserPrincipal userPrincipal,
            @Valid @ModelAttribute("planPackage") PlanPackageDTO planPackageDTO,
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
