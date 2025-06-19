package com.example.ex4.service;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class CheckoutService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Transactional
    public void processCheckout(AppUser user, List<PlanPackage> plans) {
        plans.forEach(plan -> {
           subscriptionService.createSubscription(user, plan);
           transactionService.createTransaction(user, plan.getProviderProfile().getAppUser(), Integer.parseInt(plan.getPrice()));
        });
    }
}
