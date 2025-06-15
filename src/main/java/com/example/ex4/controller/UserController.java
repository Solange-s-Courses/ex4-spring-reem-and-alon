package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PlanPackageService packageService;

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart shoppingCart;

    @GetMapping
    public String userIndex(Model model, Principal principal) {
        model.addAttribute("shoppingCart", shoppingCart.getProducts());
        model.addAttribute("userName", principal.getName());
        model.addAttribute("results",null);
        return "user/index";
    }

//    @GetMapping("/cart")
//    public String userCart(Model model) {
//        model.addAttribute("shoppingCart", shoppingCart.getProducts());;
//        return "user/cart";
//    }
}
