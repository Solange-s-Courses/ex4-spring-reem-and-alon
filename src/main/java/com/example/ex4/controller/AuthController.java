package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.constants.ProviderType;
import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.ProviderProfileService;
import com.example.ex4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProviderProfileService providerProfileService;

    @GetMapping("/")
    public String redirectAfterLogin(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        String role = userPrincipal.getUser().getRole();
        switch (role) {
            case "ADMIN":
                ProviderProfile profile = providerProfileService.findProviderProfile(userPrincipal.getUser());
                return profile.isApproved() ? "redirect:/admin" : "redirect:/login?pending";

            case "USER":
                return "redirect:/user/init-session";

            case "SUPER_ADMIN":
                return "redirect:/super-admin";

            default:
                return "redirect:/login?error";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user" , new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.addNewUser(user,"USER");
            redirectAttributes.addFlashAttribute("message", "Registration successful. Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/register-admin")
    public String showRegisterFormAdmin(Model model) {
        model.addAttribute("admin" , new AdminRegistrationFormDTO());
        model.addAttribute("providers", ProviderType.values());
        return "register-admin";
    }

    @PostMapping("/register-admin")
    public String processRegisterAdmin(@Valid @ModelAttribute("admin") AdminRegistrationFormDTO adminForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("providers", ProviderType.values());
            return "register-admin";
        }
        try {
            providerProfileService.registerProviderProfile(adminForm);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Awaiting super admin approval. you can login for meanwhile");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register-admin";
        }
    }
}

