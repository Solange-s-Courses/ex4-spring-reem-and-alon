package com.example.ex4.components;

import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public SubscriptionDTO toDto(Subscription subscription) {
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
