package com.example.ex4.service;

import com.example.ex4.constants.TransactionType;
import com.example.ex4.entity.*;
import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    public void createPaymentTransaction(Subscription subscriber, BigDecimal monthlyCost) {
        Transaction transaction = Transaction.builder().subscription(subscriber).chargePrice(monthlyCost)
                .timestamp(LocalDateTime.now()).type(TransactionType.TRANSFER)
                .build();
        transactionRepo.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllProviderTransactions(ProviderProfile profile) {
        List<Subscription> subscriptions = subscriptionRepo.findAllByPlanPackageOption_PlanPackage_ProviderProfile(profile);
        return subscriptions.stream()
                .flatMap(sub -> sub.getPaymentTransactions().stream())
                .toList();
    }
}
