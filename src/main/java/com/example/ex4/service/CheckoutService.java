package com.example.ex4.service;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.components.UserSessionSubscriptions;
import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.entity.User;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ShoppingCart sessionCart;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PlanPackageService planPackageService ;
    @Autowired
    private PlanPackageRepository planPackageRepository;

    @Transactional
    public void processCheckout(User user) {
        Set<Long> pkgIds = sessionCart.getItems().stream()
                .map(CartItemDTO::getPkgId)
                .collect(Collectors.toSet());

        List<PlanPackage> plans = planPackageService.findAllProducts(pkgIds);
        if (!subscriptionRepository.existsByUserAndPlanPackageIn(user, plans))
            throw new RuntimeException("Already subscribed for some of the packages");

        validateCheckout(user.getCreditBalance(), plans);

        plans.forEach(plan -> {
            Subscription newSubscription = subscriptionService.createSubscription(user, plan);
            transactionService.createTransaction(newSubscription, plan.getMonthlyCost());
        });
        plans.stream()
                .map(PlanPackage::getProviderProfile)
                .distinct()
                .forEach(provider ->
                        chatService.getOrCreate(user, provider)
                );
    }

    public void validateCheckout(User user, List<PlanPackage> plans) {
        int totalCost = plans.stream().map(PlanPackage::getMonthlyCost).reduce(0, Integer::sum);

        if (totalCost > userCredit) {
            throw new RuntimeException("not enough credit balance to proceed checkout");
        }

        if (subscriptionRepository.existsAllBypla)
    }
}
