package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@ControllerAdvice(assignableTypes = {UserController.class, SearchProviderController.class, CheckoutController.class})
public class AdminControllerAdvice {

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanPackageService planPackageService;

    @ModelAttribute("shoppingCart")
    public List<PlanPackage> shoppingCart() {
        return planPackageService.findAllProducts(sessionCart.getProducts());
    }

    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute("balance")
    public int balance(Principal principal) {
        return userService.findUserBalance(principal.getName());
    }
}
