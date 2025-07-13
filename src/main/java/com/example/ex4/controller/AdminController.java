package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.dto.PlanPackageOptionDTO;
import com.example.ex4.entity.*;
import com.example.ex4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * Service for business logic of {@link Period}.
     */
    @Autowired
    private PeriodService periodService;

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
        List<PlanPackageDTO> plans = planPackageService.getAllProviderPackages(providerProfile);
        List<Transaction> transactions = transactionService.findAllProviderTransactions(providerProfile);

        for (PlanPackageDTO planPackage : plans) {
            System.out.println(planPackage);
        }

        model.addAttribute("admin", admin);
        model.addAttribute("profile", providerProfile);
        model.addAttribute("plans", plans);
        model.addAttribute("transactions", transactions);
        return "admin/index";
    }

    /**
     * render the "add plan form" page
     *
     * @param userPrincipal the authenticated admin user
     * @param model transfer the data to the view
     * @return add package form page
     */
    @GetMapping("/add-package")
    public String getPackageForm(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        List<Period> periods = periodService.findAll();

        PlanPackageDTO planPackageDTO = new PlanPackageDTO();
        List<PlanPackageOptionDTO> options = new ArrayList<>();

        for (Period period : periods) {
            options.add(PlanPackageOptionDTO.builder()
                    .periodId(period.getId())
                    .periodName(period.getName())
                    .months(period.getMonths())
                    .discount(BigDecimal.ZERO)
                    .build());
        }
        ProviderProfile providerProfile = profileService.findProviderProfile(userPrincipal.getUser());

        planPackageDTO.setProviderProfileId(providerProfile.getId());
        planPackageDTO.setPlanOptions(options);

        model.addAttribute("subscriptionPeriod", periods);
        model.addAttribute("planPackage", planPackageDTO);
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
     *
     * @see PlanPackageDTO
     */
    @PostMapping("/add-package")
    public String addPackage(
            @AuthenticationPrincipal MyUserPrincipal userPrincipal,
            @Valid @ModelAttribute("planPackage") PlanPackageDTO planPackageDTO,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("subscriptionPeriod",  periodService.findAll());
            return "admin/add-package-form";
        }

        ProviderProfile providerProfile = profileService.findProviderProfile(userPrincipal.getUser());

        planPackageService.saveNewPackage(providerProfile, planPackageDTO);
        return "redirect:/admin";
    }
}
