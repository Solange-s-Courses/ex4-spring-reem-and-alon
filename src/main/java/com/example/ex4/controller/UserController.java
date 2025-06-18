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
        model.addAttribute("shoppingCart", shoppingCart.getProducts());
        model.addAttribute("userName", principal.getName());
        model.addAttribute("results",null);
        AppUser appUser= userService.findByUsername(principal.getName());
        model.addAttribute("balance", appUser.getBalance());
        return "user/index";
    }
    @PostMapping("/balance/add")
    @Transactional
    public String loadBalance(@RequestParam Integer amount, Principal principal,Model model) {
        AppUser appUser= userService.findByUsername(principal.getName());
        appUser.setBalance(appUser.getBalance()+amount);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("balance", appUser.getBalance());
        System.out.println(appUser.getBalance());

       return "user/index";
    }
}
