package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.ProviderProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

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
                return "redirect:/user";

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
}

