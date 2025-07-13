package com.example.ex4.repository;

import com.example.ex4.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link Subscription} entities.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Finds all subscriptions for a user.
     *
     * @param user the user
     * @return list of subscriptions
     */
    List<Subscription> findSubscriptionByUser(User user);

    /**
     * Finds all subscriptions for a specific provider.
     *
     * @param provider the provider profile
     * @return list of subscriptions related to the provider
     */
    List<Subscription> findAllByPlanPackageOption_PlanPackage_ProviderProfile(ProviderProfile provider);

    /**
     * Checks if a user has any subscription to the given list of package options.
     *
     * @param user the user
     * @param plans the list of plan package options
     * @return true if user is subscribed to any of the given plans
     */
    boolean existsByUserAndPlanPackageOptionIn(User user, List<PlanPackageOption> plans);

    /**
     * Checks if a user is subscribed to a plan package.
     *
     * @param user the user
     * @param planPackage the plan package
     * @return true if user is subscribed to the plan package
     */
    boolean existsByUserAndPlanPackageOption_PlanPackage(User user, PlanPackage planPackage);
}
