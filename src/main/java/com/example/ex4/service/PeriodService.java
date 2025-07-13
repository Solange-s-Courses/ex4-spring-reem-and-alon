package com.example.ex4.service;

import com.example.ex4.entity.Period;
import com.example.ex4.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for accessing and managing subscription periods.
 *
 * @see Period
 */
@Service
public class PeriodService {
    @Autowired
    private PeriodRepository periodRepository;

    /**
     * Returns all available periods.
     * @return list of all periods
     */
    public List<Period> findAll() {
        System.out.println(periodRepository.findAll());
        System.out.println("HIIII");
        return periodRepository.findAll();
    }
}
