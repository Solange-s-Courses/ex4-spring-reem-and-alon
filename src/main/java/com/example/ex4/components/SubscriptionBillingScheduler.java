package com.example.ex4.components;

import com.example.ex4.repository.SubscriptionRepository;
import com.example.ex4.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for processing recurring monthly charges for subscriptions.
 * <p>
 * This component runs automatically once a day at 03:00 AM and
 * charges all subscriptions for which more than one month has passed
 * since their last charge.
 * </p>
 */
@Component
public class SubscriptionBillingScheduler {

    /** Service for payment and subscription transaction business logic. */
    @Autowired
    private TransactionService transactionService;

    /**
     * Charges all subscriptions whose next scheduled charge date has arrived or passed,
     * by creating a new payment transaction for each.
     * <p>
     * This method is scheduled to run automatically every day at 03:00 AM.
     * For each eligible subscription, the {@link TransactionService} is called to perform the charge,
     * based on the subscription start date, period, and number of previous charges.
     * Any errors during the charge are logged to the console.
     * </p>
     *
     * @see SubscriptionRepository
     * @see TransactionService
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void chargeMonthlySubscriptions() {
        transactionService.chargeSubscribers();
    }

}
