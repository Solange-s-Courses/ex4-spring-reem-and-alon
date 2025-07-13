package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.service.ProviderProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication requests.
 * <p>
 * Responsible redirecting the logged-in user to its relevant page (base on its role)
 */
@Controller
public class AuthController {

    /**
     * Service for business logic of {@link ProviderProfile}.
     */
    @Autowired
    private ProviderProfileService providerProfileService;

    /**
     * this method redirect the user to its index page
     *
     * @param userPrincipal the authenticated admin user
     * @return the index page of the logged-in user based on its role
     */
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

    /**
     * renders the "login page"
     *
     * @return login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

