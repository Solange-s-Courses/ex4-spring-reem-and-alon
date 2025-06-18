package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(assignableTypes = {UserController.class, SearchProviderController.class, CartPageController.class})
public class AdminControllerAdvice {

    @ModelAttribute("shoppingCart")
    public List<PlanPackage> shoppingCart(@AuthenticationPrincipal AppUser appUser,
                                          PlanPackageService packageService, ShoppingCart shoppingCart) {
        return packageService.findAllProducts(shoppingCart.getProducts());
    }

    @ModelAttribute("userName")
    public String userName(@AuthenticationPrincipal AppUser appUser) {
        return appUser != null ? appUser.getUserName() : null;
    }

    @ModelAttribute("balance")
    public Integer balance(@AuthenticationPrincipal AppUser appUser) {
        return appUser != null ? appUser.getBalance() : null;
    }
}
