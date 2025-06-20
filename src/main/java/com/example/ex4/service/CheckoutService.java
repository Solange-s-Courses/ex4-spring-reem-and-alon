package com.example.ex4.service;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Qualifier("sessionBeanCart")
    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanPackageService planPackageService ;

    @Transactional
    public void processCheckout(String username) {
        AppUser user = userService.findByUsername(username);
        List<PlanPackage> plans = planPackageService.findAllProducts(sessionCart.getProducts());

        BigDecimal totalCost = plans.stream().map(PlanPackage::getMonthlyCost).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCost.compareTo(user.getCreditBalance()) > 0) {
            throw new RuntimeException("not enough credit balance to proceed checkout");
        }

        completeCheckout(user, plans);

    }

    private void completeCheckout(AppUser user, List<PlanPackage> plans) {
        plans.forEach(plan -> {
            Subscription newSubscription = subscriptionService.createSubscription(user, plan);
            transactionService.createTransaction(newSubscription, plan.getMonthlyCost());
        });
    }
}
