package com.example.ex4.controller;

import com.example.ex4.components.UserHolder;
import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Subscription;
import com.example.ex4.service.SubscriptionService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserHolder userHolder;

    @GetMapping
    public String userIndex(Model model) {
        model.addAttribute("userName", userHolder.getUser().getUserName());
        model.addAttribute("subscriptions",subscriptionService.findUserSubscriptions(userHolder.getUser()));
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@RequestParam int amount, Principal principal) {
        userService.depositToBalance(userHolder.getUser(), amount);
        return "redirect:/user";
    }
}
