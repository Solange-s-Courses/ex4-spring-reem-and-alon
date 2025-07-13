package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.CheckoutProviders;
import com.example.ex4.components.ShoppingCart;
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
 * Controller for handling user checkout operations.
 * <p>
 * Responsible for managing user purchases of all items in the shopping cart at once,
 * and handling the transition to the chat creation process with selected providers.
 *
 * @see ShoppingCart
 */
@Controller
@RequestMapping("user/checkout")
public class CheckoutController {

    /**
     * Session-scoped shopping cart for the current user.
     */
    @Autowired
    private ShoppingCart sessionCart;

    /**
     * Service layer for checkout business logic.
     */
    @Autowired
    private CheckoutService checkoutService;

    /**
     * Service for managing and retrieving plan packages and options.
     */
    @Autowired
    private PlanPackageService planPackageService;

    /**
     * Request-scoped bean that holds the providers of the plans in the current checkout.
     */
    @Autowired
    private CheckoutProviders planOwnerProviders;

    /**
     * Displays the current user's shopping cart and its contents.
     * <p>
     * If the checkout was successful, clears the shopping cart.
     *
     * @param success optional query param indicating if checkout succeeded
     * @param model   Spring MVC model for view attributes
     * @return the cart page view
     */
    @GetMapping
    public String cartPage(@RequestParam(value = "success", required = false) Boolean success, Model model) {
        if (success != null && success) {
            sessionCart.clear();
        }
        Set<Long> pkgIds = sessionCart.getPkgIds();
        model.addAttribute("cartItems", planPackageService.getPlanOptionsByIds(pkgIds));
        return "user/checkout-cart";
    }

    /**
     * Performs the checkout for all items in the user's cart.
     * <p>
     * After purchase, collects all relevant providers and forwards to chat creation.
     *
     * @param userPrincipal the authenticated user principal
     * @return a forward to the chat creation endpoint
     */
    @PostMapping
    public String checkout(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        List<PlanPackageOption> plansToPurchase = planPackageService.getPlanOptionsByIds(sessionCart.getPkgIds());
        checkoutService.processCheckout(user, plansToPurchase);
        Set<ProviderProfile> uniqueProviders = plansToPurchase.stream()
                .map(period -> period.getPlanPackage().getProviderProfile())
                .collect(Collectors.toSet());

        planOwnerProviders.setProviders(new ArrayList<>(uniqueProviders));
        return "forward:/chat/create";
    }

    /**
     * Handles runtime exceptions during checkout, sets error flash attribute and redirects back to the cart.
     *
     * @param model attributes for redirect
     * @param ex the exception caught
     * @return redirect to the cart page
     */
    @ExceptionHandler({RuntimeException.class})
    public String handleException(RedirectAttributes model, Exception ex) {
        model.addFlashAttribute("error", ex.getMessage());
        return "redirect:/user/checkout";
    }
}
