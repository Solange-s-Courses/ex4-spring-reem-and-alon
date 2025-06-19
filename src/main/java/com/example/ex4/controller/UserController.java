package com.example.ex4.controller;

import com.example.ex4.entity.AppUser;
import com.example.ex4.service.SubscriptionService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SubscriptionService subscriptionService;

    @GetMapping
    public String userIndex(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("results", subscriptionService.findUserSubscriptions(principal.getName()));
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@RequestParam int amount, Principal principal) {
        userService.depositToBalance(principal.getName(), amount);
        return "redirect:/user";
    }
}
