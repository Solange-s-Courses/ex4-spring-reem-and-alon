package com.example.ex4.components;

import com.example.ex4.entity.Period;
import com.example.ex4.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class PeriodInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PeriodRepository periodRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addIfNotExists("Yearly", 12);
        addIfNotExists("Half Yearly", 6);
        addIfNotExists("Three Month", 3);
    }

    private void addIfNotExists(String name, int months) {
        periodRepository.findByName(name)
                .orElseGet(() -> periodRepository.save(
                        Period.builder().name(name).months(months).build()
                ));
    }
}
