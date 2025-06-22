package com.example.ex4.service;

import com.example.ex4.components.RequestCartAction;
import com.example.ex4.components.UserHolder;
import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
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

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private RequestCartAction requestCartAction;

    public Subscription createSubscription(AppUser user, PlanPackage plan) {
        if (plan.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot subscribe service plan package. because its not available anymore.");
        }
        Subscription sub = Subscription.builder().appUser(user).planPackage(plan).startDate(LocalDate.now()).build();
        return subscriptionRepository.save(sub);
    }

    public List<Subscription> findUserSubscriptions(AppUser user) {
        return subscriptionRepository.findSubscriptionByAppUser(user);
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }


    public List<PlanPackage> findUserSubscriptionPlans() {
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionByAppUser(userHolder.getUser());
        return subscriptions.stream().map(Subscription::getPlanPackage).collect(Collectors.toList());
    }
}
