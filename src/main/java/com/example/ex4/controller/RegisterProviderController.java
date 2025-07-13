package com.example.ex4.controller;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.service.ProviderCategoryService;
import com.example.ex4.service.ProviderProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

/**
 * Controller for handling provider registration by admin.
 *
 * @see ProviderCategoryService
 * @see ProviderProfileService
 */
@RequestMapping("/register/provider")
@Controller
public class RegisterProviderController {

    /**
     * Service for provider category operations.
     */
    @Autowired
    private ProviderCategoryService providerCategoryService;

    /**
     * Service for provider profile operations.
     */
    @Autowired
    private ProviderProfileService providerProfileService;

    /**
     * Displays the registration form for a new admin/provider.
     *
     * @param model the Spring model to add attributes
     * @return the name of the registration form view
     */
    @GetMapping
    public String showRegisterFormAdmin(Model model) {
        model.addAttribute("admin", new AdminRegistrationFormDTO());
        model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
        return "register-admin";
    }

    /**
     * Processes the admin/provider registration form.
     *
     * @param admin the form data for the new admin/provider
     * @param result binding result for validation errors
     * @param model the Spring model to add attributes
     * @param redirectAttributes attributes for flash scope after redirect
     * @return redirect to log in on success, or the registration form on error
     */
    @PostMapping
    public String processRegisterAdmin(@Valid @ModelAttribute("admin") AdminRegistrationFormDTO admin,
                                       BindingResult result,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("admin", admin);
            model.addAttribute("providers", providerCategoryService.findAllCategoryNames());
            result.getAllErrors().forEach(System.out::println);
            return "register-admin";
        }
        try {
            providerProfileService.registerProviderProfile(admin);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Awaiting super admin approval");
            return "redirect:/login";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("userName", "username in use. please enter new username");
            return "register-admin";
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
