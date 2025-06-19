package com.example.ex4.service;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Transaction;
import com.example.ex4.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    public void createTransaction(AppUser from, AppUser to, int money) {
        Transaction transaction = Transaction.builder().fromUser(from).toProvider(to).amount(money).build();
        transactionRepo.save(transaction);
    }
}
