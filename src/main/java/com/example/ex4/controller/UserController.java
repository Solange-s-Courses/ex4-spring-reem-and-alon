package com.example.ex4.controller;

import com.example.ex4.components.SubscriptionMapper;
import com.example.ex4.components.UserHolder;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @GetMapping
    public String userIndex(Model model) {
        if (!userSubscriptions.isEmpty()){
            model.addAttribute("subscriptions", userSubscriptions.getSubscriptions());
        }
        model.addAttribute("userName", userHolder.getUser().getUserName());
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@RequestParam int amount) {
        userService.depositToBalance(userHolder.getUser(), amount);
        return "redirect:/user";
    }
}
