package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanPackageRepository extends JpaRepository<PlanPackage, Long> {
    Optional<List<PlanPackage>> findAllByProviderProfile(ProviderProfile profile);

    PlanPackage findByIdAndTitle(Long id, String title);

    boolean existsByIdAndTitle(Long pkgId, String subPkgName);
}