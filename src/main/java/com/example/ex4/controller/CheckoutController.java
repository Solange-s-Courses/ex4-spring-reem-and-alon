package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.CheckoutProviders;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.service.CheckoutService;
import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for handling checkout endpoint requests
 * <p>
 * Responsible for managing user purchase action on its shopping cart
 * The user can only but all items at once
 *
 * @see ShoppingCart
 */
@Controller
@RequestMapping("user/checkout")
public class CheckoutController {

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private CheckoutService checkoutService;

    /**
     * Service for business logic of {@link PlanPackage}.
     */
    @Autowired
    private PlanPackageService planPackageService;

    /**
     * Request scope bean that holds the plan packages owner (of the items the user wants to buy)
     */
    @Autowired
    private CheckoutProviders planOwnerProviders;

    @GetMapping
    public String cartPage(@RequestParam(value = "success", required = false) Boolean success, Model model) {
        if (success != null && success) {
            sessionCart.clear();
        }
        Set<Long> pkgIds = sessionCart.getPkgIds();
        model.addAttribute("cartItems", planPackageService.getPlanOptionsByIds(pkgIds));

        return "user/checkout-cart";
    }

    @PostMapping
    public String checkout( @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        List<PlanPackageOption> plansToPurchase = planPackageService.getPlanOptionsByIds(sessionCart.getPkgIds());
        checkoutService.processCheckout(user, plansToPurchase);
        Set<ProviderProfile> uniqueProviders = plansToPurchase.stream()
                .map(period -> period.getPlanPackage().getProviderProfile())
                .collect(Collectors.toSet());

        planOwnerProviders.setProviders(new ArrayList<>(uniqueProviders));
        return "forward:/chat/create";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleException(RedirectAttributes model , Exception ex) {
        model.addFlashAttribute("error", ex.getMessage());
        return "redirect:/user/checkout";
    }
}
