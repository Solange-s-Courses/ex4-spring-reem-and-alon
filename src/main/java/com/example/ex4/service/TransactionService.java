package com.example.ex4.service;

import com.example.ex4.constants.TransactionType;
import com.example.ex4.entity.*;
import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.repository.TransactionRepository;
import com.example.ex4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing subscription payment transactions.
 *
 * @see Transaction
 */
@Service
public class TransactionService {

    /** Repository for managing transactions. */
    @Autowired
    private TransactionRepository transactionRepo;

    /** Repository for managing subscriptions. */
    @Autowired
    private SubscriptionRepository subscriptionRepo;

    /** Repository for managing users. */
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new payment transaction for a subscriber and a payment amount.
     *
     * @param subscriber the subscription being charged
     * @param monthlyCost the cost charged
     */
    @Transactional
    public void createPaymentTransaction(Subscription subscriber, BigDecimal monthlyCost) {
        User user = subscriber.getUser();

        if (user.getCreditBalance() == null)
            throw new IllegalStateException("User " + user.getId() + " has null credit balance!");

        if (monthlyCost == null)
            throw new IllegalStateException("Monthly cost is null for subscription " + subscriber.getId());

        if (user.getCreditBalance().compareTo(monthlyCost) < 0) {
            Transaction transaction = Transaction.builder()
                    .subscription(subscriber)
                    .chargePrice(monthlyCost)
                    .timestamp(LocalDateTime.now())
                    .type(TransactionType.FAILED)
                    .build();
            transactionRepo.save(transaction);
            return;
        }

        user.setCreditBalance(user.getCreditBalance().subtract(monthlyCost));
        userRepository.save(user);

        Transaction transaction = Transaction.builder()
                .subscription(subscriber)
                .chargePrice(monthlyCost)
                .timestamp(LocalDateTime.now())
                .type(TransactionType.TRANSFER)
                .build();
        transactionRepo.save(transaction);
    }

    /**
     * Returns all payment transactions.
     *
     * @return list of transactions
     */
    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    /**
     * Returns all transactions for a provider's profile.
     *
     * @param profile the provider profile
     * @return list of transactions associated with this provider
     */
    @Transactional(readOnly = true)
    public List<Transaction> findAllProviderTransactions(ProviderProfile profile) {
        List<Subscription> subscriptions = subscriptionRepo.findAllByPlanPackageOption_PlanPackage_ProviderProfile(profile);
        return subscriptions.stream()
                .flatMap(sub -> sub.getPaymentTransactions().stream())
                .toList();
    }

    /**
     * Charges all subscriptions whose next scheduled charge date has arrived or passed,
     * by creating a new payment transaction for each.
     * <p>
     * For each subscription, the next charge date is calculated as:
     * <pre>
     *   nextChargeDate = startDate.plusMonths(transactionsMade * periodMonths)
     * </pre>
     * {@code periodMonths} is the number of months in the subscription period,
     * {@code transactionsMade} is the number of existing payment transactions for the subscription.
     * {@code today >= nextChargeDate}, the subscription is charged and a transaction is created.
     *
     * <b>Assumptions:</b>
     * <ul>
     *   <li>Each transaction represents a payment for one full period (e.g., month/quarter/year).</li>
     *   <li>The subscription's {@code startDate} is the anchor for all period calculations.</li>
     *   <li>Changing the subscription period or startDate after activation may require a custom logic.</li>
     *   <li>If more than one period has passed since the last charge, this implementation only charges once per run (no retroactive catch-up).</li>
     * </ul>
     *
     * This method is intended to be called from a scheduled job (e.g., daily).
     */
    @Transactional
    public void chargeSubscribers() {
        LocalDate today = LocalDate.now();
        List<Subscription> subscriptions = subscriptionRepo.findAll();

        for (Subscription subscription : subscriptions) {
            int periodMonths = subscription.getPlanPackageOption().getPeriod().getMonths();
            LocalDate startDate = subscription.getStartDate();
            int transactionsMade = subscription.getPaymentTransactions().size();

            LocalDate nextChargeDate = startDate.plusMonths((long) transactionsMade * periodMonths);

            if (!today.isBefore(nextChargeDate)) {
                createPaymentTransaction(subscription, subscription.getPlanPackageOption().getOptionPrice());
            }
        }
    }
}
