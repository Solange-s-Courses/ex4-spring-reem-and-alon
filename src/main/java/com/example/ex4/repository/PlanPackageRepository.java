package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanPackageRepository extends JpaRepository<PlanPackage, Long> {
    Optional<List<PlanPackage>> findAllByProviderProfile(ProviderProfile profile);
    List<PlanPackage> findAllByProviderProfileIn(List<ProviderProfile> profiles);

    List<PlanPackage> findAllByProviderProfile_Category(ProviderCategory providerCategory);
}