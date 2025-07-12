package com.example.ex4.repository;

import com.example.ex4.entity.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findSubscriptionByUser(User user);

    List<Subscription> findAllByPlanPackageOption_PlanPackage_ProviderProfile(ProviderProfile provider);

    boolean existsByUserAndPlanPackageOptionIn(User user, List<PlanPackageOption> plans);

    boolean existsByUserAndPlanPackageOption_PlanPackage(User user, PlanPackage planPackage);


    List<Subscription> findByUserAndPlanPackageOption_PlanPackage(User user, PlanPackage planPackage);
}
