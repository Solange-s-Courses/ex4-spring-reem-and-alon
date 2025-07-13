package com.example.ex4.repository;

import com.example.ex4.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing {@link Period} entities
 */
@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    /**
     * Finds a period by a given name
     *
     * @param name The name of the period (Yearly, monthly, ect…)
     * @return an optional containing the period
     */
    Optional<Period> findByName(String name);
}
