package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.SubscriptionService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ShoppingCart sessionCart;

    @GetMapping
    public String userIndex(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        model.addAttribute("subscriptions", subscriptionService.findUserSubscriptions(userPrincipal.getUser()));
        model.addAttribute("userName", userPrincipal.getUser().getUserName());
        model.addAttribute("cartItems",sessionCart.getItems());
        return "user/index";
    }

    @PostMapping("/balance/add")
    public String depositCredit(@AuthenticationPrincipal MyUserPrincipal userPrincipal,@RequestParam int amount) {
        userService.depositToBalance(userPrincipal.getUser(), amount);
        return "redirect:/user";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "You must enter a valid integer number!");
        return "user/index";
    }
}
