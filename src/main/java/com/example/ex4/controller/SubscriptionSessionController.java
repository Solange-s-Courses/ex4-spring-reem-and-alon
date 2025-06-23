package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.entity.Subscription;
import com.example.ex4.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class SubscriptionSessionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;


    @GetMapping("/user/init-session")
    public String initUserSession(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        List<Subscription> subscriptions = subscriptionService.findUserSubscriptions(userPrincipal.getUser());
        if (subscriptions != null && !subscriptions.isEmpty()) {
            userSubscriptions.setSubscriptions(subscriptions);
        }
        return "redirect:/user";
    }
}
