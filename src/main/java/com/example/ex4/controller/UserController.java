package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.AppUser;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
        model.addAttribute("shoppingCart", packageService.findAllProducts(shoppingCart.getProducts()));
        model.addAttribute("userName", principal.getName());
        model.addAttribute("results",null);
        AppUser appUser= userService.findByUsername(principal.getName());
        model.addAttribute("balance", appUser.getBalance());
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@RequestParam int amount, Principal principal) {
        userService.depositToBalance(principal.getName(), amount);
        return "redirect:/user";
    }
}
