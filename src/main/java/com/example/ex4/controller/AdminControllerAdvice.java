package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice(assignableTypes = {UserController.class, SearchProviderController.class, CheckoutController.class})
public class AdminControllerAdvice {

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @Autowired
    private UserService userService;

    @ModelAttribute("cartSize")
    public Integer cartSize() {
        return sessionCart.getProductsAmount();
    }

    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute("balance")
    public Integer balance(Principal principal) {
        return userService.findUserBalance(principal.getName());
    }
}
