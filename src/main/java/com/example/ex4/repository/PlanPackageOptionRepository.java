package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackageOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link PlanPackageOption} entities
 */
@Repository
public interface PlanPackageOptionRepository extends JpaRepository<PlanPackageOption, Long> {
}
