package com.example.ex4.service;

import com.example.ex4.entity.Period;
import com.example.ex4.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    @Autowired
    private PeriodRepository periodRepository;

    public List<Period> findAll() {
        System.out.println(periodRepository.findAll());
        System.out.println("HIIII");
        return periodRepository.findAll();
    }
}
