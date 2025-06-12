package com.example.ex4.repository;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<List<Subscription>> findAllSubscriptionByAppUser_Id(Long appUserId);
    List<Subscription> findByPlanPackage_PackageType(String packageType);
}
