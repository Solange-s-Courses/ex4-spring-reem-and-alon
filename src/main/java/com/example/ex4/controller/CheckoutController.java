package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.service.CheckoutService;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user/checkout")
public class CheckoutController {

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("shoppingCart", planPackageService.findAllProducts(sessionCart.getProducts()));
        return "user/checkout-cart";
    }

    @PostMapping
    public String checkout( @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        checkoutService.processCheckout(userPrincipal.getUser());
        userSubscriptions.setSubscriptions(subscriptionService.findUserSubscriptions(userPrincipal.getUser()));
        sessionCart.clear();
        return "redirect:/user/checkout?success=true";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleException(RedirectAttributes model , Exception ex) {
        model.addFlashAttribute("error", ex.getMessage());
        return "redirect:/user/checkout";
    }
}
