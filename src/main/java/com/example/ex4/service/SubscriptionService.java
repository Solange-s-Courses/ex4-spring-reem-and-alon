package com.example.ex4.service;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void createSubscription(AppUser user, PlanPackage plan) {
        Subscription sub = Subscription.builder().appUser(user).planPackage(plan).startDate(LocalDate.now()).build();
        subscriptionRepository.save(sub);
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }
}
