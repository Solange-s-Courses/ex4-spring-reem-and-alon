package com.example.ex4.service;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


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

    public List<Subscription> findUserSubscriptions(String username) {
        return subscriptionRepository.getSubscriptionsByAppUser_UserName(username);
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }
}
