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

/**
 * Service class handling the checkout process, including validations and subscription creation.
 *
 * @see SubscriptionService
 * @see TransactionService
 * @see SubscriptionRepository
 * @see ShoppingCart
 */
@Service
public class CheckoutService {

    /**
     * Service for handling payment transactions.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * Service for creating and managing subscriptions.
     */
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Repository for accessing subscription data.
     */
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Shopping cart for the current session/user.
     */
    @Autowired
    private ShoppingCart shoppingCart;

    /**
     * Processes the checkout flow:
     * Validates selected plans and user balance, creates subscriptions, and generates payment transactions.
     *
     * @param user the user making the purchase
     * @param plans the list of plan package options being purchased
     * @throws RuntimeException if validation fails for any reason
     */
    @Transactional
    public void processCheckout(User user, List<PlanPackageOption> plans) {
        validateCheckout(user, plans);
        plans.forEach(plan -> {
            Subscription newSubscription = subscriptionService.createSubscription(user, plan);
            transactionService.createPaymentTransaction(newSubscription, plan.getOptionPrice());
        });
    }

    /**
     * Validates the checkout:
     * <ul>
     *     <li>Ensures there are items in the cart</li>
     *     <li>Ensures no duplicate subscriptions for the same plan option</li>
     *     <li>Prevents multiple subscriptions for the same plan</li>
     *     <li>Checks that the user has sufficient credit</li>
     * </ul>
     *
     * @param user the user performing checkout
     * @param plans list of plan options being purchased
     * @throws RuntimeException if any validation fails
     */
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
