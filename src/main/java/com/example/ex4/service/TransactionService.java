package com.example.ex4.service;

import com.example.ex4.entity.Subscription;
import com.example.ex4.entity.Transaction;
import com.example.ex4.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    public void createTransaction(Subscription subscriber, int monthlyCost) {
        Transaction transaction = Transaction.builder().subscription(subscriber).amount(monthlyCost).build();
        transactionRepo.save(transaction);
    }
}
