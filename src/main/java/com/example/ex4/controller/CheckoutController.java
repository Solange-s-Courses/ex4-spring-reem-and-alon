package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.CheckoutService;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PlanPackageService planPackageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("shoppingCart", planPackageService.findAllProducts(sessionCart.getProducts()));
        return "user/checkout-cart";
    }

    @PostMapping
    public String checkout(Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        List<PlanPackage> plans = planPackageService.findAllProducts(sessionCart.getProducts());
        checkoutService.processCheckout(user, plans);
        sessionCart.clear();
        return "redirect:/user/checkout?success=true";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleException(RedirectAttributes model , Exception ex) {
        model.addFlashAttribute("error", ex.getMessage());
        return "redirect:/user/checkout";
    }
}
