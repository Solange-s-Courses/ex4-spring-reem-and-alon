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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

/**
 * Controller for user actions such as viewing subscriptions and depositing credit.
 *
 * @see UserService
 * @see SubscriptionService
 * @see ShoppingCart
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Service for user operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Service for subscription operations.
     */
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Shopping cart for the session.
     */
    @Autowired
    private ShoppingCart sessionCart;

    /**
     * Displays the user home page, including subscriptions and cart items.
     *
     * @param userPrincipal authenticated user principal
     * @param model Spring model to add attributes
     * @return the user index view
     * @see SubscriptionService#findUserSubscriptions(com.example.ex4.entity.User)
     */
    @GetMapping
    public String userIndex(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        model.addAttribute("subscriptions", subscriptionService.findUserSubscriptions(userPrincipal.getUser()));
        model.addAttribute("userName", userPrincipal.getUser().getUserName());
        model.addAttribute("cartItems", sessionCart.getItems());
        return "user/index";
    }

    /**
     * Deposits credit to the user's balance.
     *
     * @param userPrincipal authenticated user principal
     * @param amount the amount to deposit
     * @param redirectAttributes Spring RedirectAttributes for error message
     * @return redirect to user index, or reload with error if invalid amount
     * @see UserService#depositToBalance(com.example.ex4.entity.User, BigDecimal)
     */
    @PostMapping("/balance/add")
    public String depositCredit(@AuthenticationPrincipal MyUserPrincipal userPrincipal, @RequestParam BigDecimal amount, Model model) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("errorMessage", "Amount must be greater than 0");
            model.addAttribute("subscriptions", subscriptionService.findUserSubscriptions(userPrincipal.getUser()));
            return "user/index";
        }
        userService.depositToBalance(userPrincipal.getUser(), amount);
        return "redirect:/user";
    }

    /**
     * Handles argument type mismatches, e.g., invalid input when expecting a number.
     *
     * @param ex exception instance
     * @param model Spring model to display the error
     * @return the user index view with error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "You must enter a valid integer number!");
        return "user/index";
    }
}
