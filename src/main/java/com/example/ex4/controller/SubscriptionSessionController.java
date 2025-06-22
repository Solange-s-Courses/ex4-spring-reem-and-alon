package com.example.ex4.controller;

import com.example.ex4.components.UserHolder;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.entity.Subscription;
import com.example.ex4.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class SubscriptionSessionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserSessionSubscriptions userSubscriptions;

    @Autowired
    private UserHolder userHolder;

    @GetMapping("/user/init-session")
    public String initUserSession() {
        List<Subscription> subscriptions = subscriptionService.findUserSubscriptions(userHolder.getUser());
        if (subscriptions != null && !subscriptions.isEmpty()) {
            userSubscriptions.setSubscriptions(subscriptions);
        }
        return "redirect:/user";
    }
}
