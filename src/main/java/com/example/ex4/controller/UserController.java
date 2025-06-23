package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.SubscriptionMapper;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @GetMapping
    public String userIndex(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        if (!userSubscriptions.isEmpty()){
            System.out.println("Hellloooooo");
            model.addAttribute("subscriptions", userSubscriptions.getSubscriptions());
        }
        else {
            System.out.println("byeeeeeee");

        }

        model.addAttribute("userName", userPrincipal.getUser().getUserName());
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@AuthenticationPrincipal MyUserPrincipal userPrincipal,@RequestParam int amount) {
        userService.depositToBalance(userPrincipal.getUser(), amount);
        return "redirect:/user";
    }
}
