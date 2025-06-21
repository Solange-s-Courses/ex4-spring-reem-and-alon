package com.example.ex4.service;

import com.example.ex4.constants.TransactionType;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Subscription;
import com.example.ex4.entity.Transaction;
import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

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
}
