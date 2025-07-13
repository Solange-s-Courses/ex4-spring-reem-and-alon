package com.example.ex4.controller;

import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Transaction;
import com.example.ex4.service.ProviderCategoryService;
import com.example.ex4.service.ProviderProfileService;
import com.example.ex4.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * Controller for super admin actions, such as approving/rejecting provider profiles
 * and managing provider categories.
 *
 * @see ProviderProfileService
 * @see ProviderCategoryService
 * @see TransactionService
 */
@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {

    /**
     * Service for provider profile operations.
     */
    @Autowired
    private ProviderProfileService providerProfileService;

    /**
     * Service for transaction operations.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Service for provider category operations.
     */
    @Autowired
    private ProviderCategoryService providerCategoryService;

    /**
     * Displays the landing page for the super admin, with pending profiles,
     * transaction history, and category list.
     *
     * @param model Spring model to add attributes
     * @return the super admin index view
     */
    @GetMapping
    public String landingPage(Model model) {
        List<ProviderProfile> pendingProfiles = providerProfileService.findAllPendingProfiles();
        List<Transaction> transactionHistory = transactionService.findAll();
        List<String> existingCategories = providerCategoryService.findAllCategoryNames();
        model.addAttribute("pendingProfiles", pendingProfiles);
        model.addAttribute("transactions", transactionHistory);
        model.addAttribute("existingCategories", existingCategories);
        return "super_admin/index";
    }

    /**
     * Approves a provider profile.
     *
     * @param id the profile id
     * @param redirectAttributes redirect attributes for feedback messages
     * @return redirect to super admin index
     * @see ProviderProfileService#activateAdminAccount(long)
     */
    @PostMapping("/provider/approve/{id}")
    public String approveProfile(@PathVariable long id, RedirectAttributes redirectAttributes) {
        providerProfileService.activateAdminAccount(id);
        redirectAttributes.addFlashAttribute("message", "Profile approved.");
        return "redirect:/super-admin";
    }

    /**
     * Rejects and removes a provider profile.
     *
     * @param id the profile id
     * @param redirectAttributes redirect attributes for feedback messages
     * @return redirect to super admin index
     * @see ProviderProfileService#removeProviderProfile(long)
     */
    @DeleteMapping("/provider/reject/{id}")
    public String rejectProfile(@PathVariable long id, RedirectAttributes redirectAttributes) {
        providerProfileService.removeProviderProfile(id);
        redirectAttributes.addFlashAttribute("message", "Profile rejected and removed.");
        return "redirect:/super-admin";
    }

    /**
     * Adds a new provider category, if not empty and does not already exist.
     *
     * @param categoryName the category name to add
     * @param redirectAttributes redirect attributes for feedback messages
     * @return redirect to super admin index
     * @see ProviderCategoryService#addCategory(String)
     * @see ProviderCategoryService#categoryExists(String)
     */
    @PostMapping("/provider/add-category")
    public String addProviderCategory(@RequestParam String categoryName,
                                      RedirectAttributes redirectAttributes) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Category name cannot be empty");
            return "redirect:/super-admin";
        }
        String cleanCategoryName = categoryName.trim().toUpperCase();
        if (providerCategoryService.categoryExists(cleanCategoryName)) {
            redirectAttributes.addFlashAttribute("error", "Category already exists: " + cleanCategoryName);
            return "redirect:/super-admin";
        }
        providerCategoryService.addCategory(cleanCategoryName);
        redirectAttributes.addFlashAttribute("success", "Category added successfully: " + cleanCategoryName);

        return "redirect:/super-admin";
    }
}
