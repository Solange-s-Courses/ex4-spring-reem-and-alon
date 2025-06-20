package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.CheckoutService;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("user/checkout")
public class CheckoutController {

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PlanPackageService planPackageService;

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("shoppingCart", planPackageService.findAllProducts(sessionCart.getProducts()));
        return "user/checkout-cart";
    }

    @PostMapping
    public String checkout(Principal principal) {
        checkoutService.processCheckout(principal.getName());
        sessionCart.clear();
        return "redirect:/user/checkout?success=true";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleException(RedirectAttributes model , Exception ex) {
        model.addFlashAttribute("error", ex.getMessage());
        return "redirect:/user/checkout";
    }
}
