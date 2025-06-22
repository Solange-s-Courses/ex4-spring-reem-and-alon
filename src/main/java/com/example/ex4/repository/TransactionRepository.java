package com.example.ex4.repository;

import com.example.ex4.entity.Subscription;
import com.example.ex4.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySubscription(Subscription subscription);
}
