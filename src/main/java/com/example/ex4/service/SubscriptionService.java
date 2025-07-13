package com.example.ex4.service;

import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.entity.Subscription;
import com.example.ex4.entity.User;
import com.example.ex4.repository.PlanPackageOptionRepository;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling user subscriptions: creation, retrieval, and validation.
 *
 * @see Subscription
 * @see PlanPackageOption
 * @see SubscriptionDTO
 */
@Service
public class SubscriptionService {

    /** Repository for managing subscriptions. */
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /** Repository for managing plan package options. */
    @Autowired
    private PlanPackageOptionRepository planPackageOptionRepository;

    /**
     * Creates and saves a new subscription for a user and a plan option.
     *
     * @param user the subscribing user
     * @param plan the plan option being subscribed to
     * @return the saved Subscription
     * @throws RuntimeException if the plan's expiry date has passed
     */
    public Subscription createSubscription(User user, PlanPackageOption plan) {
        if (plan.getPlanPackage().getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot subscribe service plan package. because its not available anymore.");
        }
        Subscription sub = Subscription.builder()
                .user(user)
                .planPackageOption(plan)
                .startDate(LocalDate.now())
                .build();
        return subscriptionRepository.save(sub);
    }

    /**
     * Retrieves all subscriptions for a user as DTOs.
     *
     * @param user the user
     * @return list of subscription DTOs (may be empty)
     */
    public List<SubscriptionDTO> findUserSubscriptions(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionByUser(user);
        if (subscriptions.isEmpty()) {
            return new ArrayList<>();
        }
        return subscriptions.stream().map(this::toSubscriptionDTO).collect(Collectors.toList());
    }

    /**
     * Maps a Subscription entity to a DTO.
     *
     * @param subscription the subscription entity
     * @return the DTO
     */
    private SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        PlanPackageOption planPackageOption = subscription.getPlanPackageOption();
        return SubscriptionDTO.builder()
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .planDescription(planPackageOption.getPlanPackage().getDescription())
                .monthlyCost(planPackageOption.getPlanPackage().getMonthlyCost())
                .providerId(planPackageOption.getPlanPackage().getProviderProfile().getId())
                .providerName(planPackageOption.getPlanPackage().getProviderProfile().getCompanyName())
                .category(planPackageOption.getPlanPackage().getProviderProfile().getCategory().getName())
                .build();
    }

    /**
     * Checks if a user already has a subscription to the given cart item.
     *
     * @param user the user
     * @param item the cart item
     * @return true if user is already subscribed, false otherwise
     * @throws IllegalArgumentException if the plan option does not exist
     */
    public boolean existsInUserSubscription(User user, CartItemDTO item) {
        PlanPackageOption planPackageOption = planPackageOptionRepository.findById(item.getPkgOptionId())
                .orElseThrow(() -> new IllegalArgumentException("Plan package option not found"));

        PlanPackage planPackage = planPackageOption.getPlanPackage();

        return subscriptionRepository.existsByUserAndPlanPackageOption_PlanPackage(user, planPackage);
    }
}

