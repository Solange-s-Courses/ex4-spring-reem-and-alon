package com.example.ex4.service;

import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(AppUser user, PlanPackage plan) {
        if (plan.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot subscribe service plan package. because its not available anymore.");
        }
        Subscription sub = Subscription.builder().appUser(user).planPackage(plan).startDate(LocalDate.now()).build();
        return subscriptionRepository.save(sub);
    }

    public List<SubscriptionDTO> findUserSubscriptions(AppUser user) {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionByAppUser(user);
        return subscriptions.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionDTO toDto(Subscription subscription) {
        PlanPackage plan = subscription.getPlanPackage();
        ProviderProfile provider = plan.getProviderProfile();

        return SubscriptionDTO.builder()
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .planDescription(plan.getDescription())
                .planName(plan.getTitle())
                .monthlyCost(plan.getMonthlyCost())
                .providerId(provider.getId())
                .providerName(provider.getProviderName())
                .category(provider.getCategory())
                .build();
    }
}
