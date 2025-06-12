package com.example.ex4.controller;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Subscription;
import com.example.ex4.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<HttpStatus> addNewSubscription(@RequestBody Subscription subscription, Principal principal) {
        subscriptionService.addSubscription(subscription, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

