package com.example.ex4.service;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.User;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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

    public List<Subscription> findUserSubscriptions(User user) {
        return subscriptionRepository.findSubscriptionByUser(user);
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }


    public List<PlanPackage> findUserSubscriptionPlans(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionByUser(user);
        return subscriptions.stream().map(Subscription::getPlanPackage).collect(Collectors.toList());
    }
}
