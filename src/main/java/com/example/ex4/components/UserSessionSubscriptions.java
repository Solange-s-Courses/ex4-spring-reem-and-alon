package com.example.ex4.components;

import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SessionScope
public class UserSessionSubscriptions {
    private List<Subscription> subscriptions;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public UserSessionSubscriptions() {
        subscriptions = new ArrayList<>();
    }

    public List<SubscriptionDTO> getSubscriptions() {
        return subscriptions.stream().map(subscriptionMapper::toDto).collect(Collectors.toList());
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public boolean isEmpty() {
        return subscriptions.isEmpty();
    }

    public Set<Long> getPackagesIds() {
        return subscriptions.stream().map(Subscription::getId).collect(Collectors.toSet());
    }
}
