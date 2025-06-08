package com.example.ex4.controller;

import com.example.ex4.entity.AppUser;
import com.example.ex4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user" , new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") AppUser user, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful. Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}

