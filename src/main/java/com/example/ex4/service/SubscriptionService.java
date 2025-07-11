package com.example.ex4.service;

import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.User;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(User user, PlanPackage plan) {
        if (plan.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot subscribe service plan package. because its not available anymore.");
        }
        Subscription sub = Subscription.builder().user(user).planPackage(plan).startDate(LocalDate.now()).build();
        return subscriptionRepository.save(sub);
    }

    public List<SubscriptionDTO> findUserSubscriptions(User user) {
        List<Subscription> subscriptions =subscriptionRepository.findSubscriptionByUser(user);
        if (subscriptions.isEmpty()) {
            return new ArrayList<>();
        }
        return subscriptions.stream().map(this::toSubscriptionDTO).collect(Collectors.toList());
    }

    private SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        return SubscriptionDTO.builder().status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .planDescription(subscription.getPlanPackage().getDescription())
                .monthlyCost(subscription.getPlanPackage().getMonthlyCost())
                .providerId(subscription.getPlanPackage().getProviderProfile().getId())
                .providerName(subscription.getPlanPackage().getProviderProfile().getCompanyName())
                .category(subscription.getPlanPackage().getProviderProfile().getCategory().getName())
                .build();

    }

    public boolean existsInUserSubscription(User user, CartItemDTO item) {
        return subscriptionRepository.existsByUserAndPlanPackage_Id(user, item.getPkgId());
    }
}
