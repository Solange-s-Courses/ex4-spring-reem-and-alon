package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackageOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanPackageOptionRepository extends JpaRepository<PlanPackageOption, Long> {
}
