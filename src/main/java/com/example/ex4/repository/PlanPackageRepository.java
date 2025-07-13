package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanPackageRepository extends JpaRepository<PlanPackage, Long> {
    /**
     * Finds all the plan packages of the given provider
     * @param provider  Provider profile that can have many packages
     * @return Optional list of all provider plan packages
     */
    Optional<List<PlanPackage>> findAllByProviderProfile(ProviderProfile provider);
}