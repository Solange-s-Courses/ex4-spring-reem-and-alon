package com.example.ex4.repository;

import com.example.ex4.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    Optional<Period> findByName(String name);
}
