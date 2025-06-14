package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PlanPackageService packageService;

    @Autowired
    private ShoppingCart shoppingCart;

    @GetMapping
    public String userIndex(Model model, Principal principal) {
        model.addAttribute("shoppingCart", shoppingCart.getItems());
       // model.addAttribute("packages", shoppingCart.getItems());
        model.addAttribute("userName", principal.getName());
        model.addAttribute("results",null);
        System.out.println(shoppingCart.getItems().size());
        return "user/index";
    }
}
