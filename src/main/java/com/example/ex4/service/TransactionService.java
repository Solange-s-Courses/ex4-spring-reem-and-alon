package com.example.ex4.service;

import com.example.ex4.constants.TransactionType;
import com.example.ex4.entity.*;
import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    public void createTransaction(Subscription subscriber, int monthlyCost) {
        Transaction transaction = Transaction.builder()
                .subscription(subscriber)
                .amount(monthlyCost)
                .timestamp(LocalDateTime.now()).type(TransactionType.TRANSFER)
                .build();
        transactionRepo.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllProviderTransactions(List<PlanPackage> plans) {
        List<Subscription> subscriptions = plans.stream()
                .flatMap(plan->subscriptionRepo.findAllByPlanPackage(plan).stream())
                .toList();

        return subscriptions.stream()
                .flatMap(subscription -> transactionRepo.findAllBySubscription(subscription).stream())
                .toList();
    }
}
