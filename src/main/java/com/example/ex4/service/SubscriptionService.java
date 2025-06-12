package com.example.ex4.service;

import com.example.ex4.dto.SubscriptionDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Subscription;
import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getByCategory(String category) {
        List<Subscription> list = subscriptionRepository.findByPlanPackage_PackageType(category);
        return list.stream()
                .map(SubscriptionDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Subscription addSubscription(Subscription subscription, String username) {
        AppUser user = userRepository.findByUserName(username);
        return subscriptionRepository.save(subscription);
    }

    @Transactional(readOnly = true)
    public SubscriptionDTO getById(long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        return new SubscriptionDTO(sub);
    }

    public void deleteSubscription(long id) {
        subscriptionRepository.deleteById(id);
    }
}
