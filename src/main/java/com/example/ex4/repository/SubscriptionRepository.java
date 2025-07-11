package com.example.ex4.repository;

import com.example.ex4.entity.User;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findSubscriptionByUser(User user);
    List<Subscription> findAllByPlanPackage(PlanPackage planPackage);

    boolean existsByUserAndPlanPackageIn(User user, List<PlanPackage> plans);

    boolean existsByUserAndPlanPackage_Id(User user, Long pkgId);
}
