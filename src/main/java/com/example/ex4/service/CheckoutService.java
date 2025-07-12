package com.example.ex4.service;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.entity.Subscription;
import com.example.ex4.entity.User;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ShoppingCart shoppingCart;

    @Transactional
    public void processCheckout(User user, List<PlanPackageOption> plans) {
        validateCheckout(user, plans);
        plans.forEach(plan -> {
            Subscription newSubscription = subscriptionService.createSubscription(user, plan);
            transactionService.createPaymentTransaction(newSubscription, plan.getOptionPrice());
        });
    }

    private void validateCheckout(User user, final List<PlanPackageOption> plans) {
        if (plans.isEmpty()) {
            throw new RuntimeException("You dont have any items in your cart!");
        }

        if (plans.stream().map(PlanPackageOption::getId).distinct().count() < plans.size()) {
            throw new RuntimeException("You cannot purchase subscription more same plan package twice!");
        }

        if (subscriptionRepository.existsByUserAndPlanPackageOptionIn(user, plans))
            throw new RuntimeException("Already subscribed for some of the packages");

        if (shoppingCart.getTotalCost().compareTo(user.getCreditBalance()) > 0){
            throw new RuntimeException("Not enough credit balance for a yearly commitment for all packages");
        }
    }
}
