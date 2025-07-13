package com.example.ex4.components;

import com.example.ex4.entity.User;
import com.example.ex4.entity.Period;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.repository.UserRepository;
import com.example.ex4.repository.PeriodRepository;
import com.example.ex4.repository.ProviderCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * {@code DataInitializer}
 * <p>
 * Initializes default data in the database after the Spring context is refreshed.
 * This includes predefined subscription periods, an admin user, and provider categories.
 */
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    /** UserRepository */
    @Autowired
    private UserRepository userRepository;

    /** PeriodRepository */
    @Autowired
    private PeriodRepository periodRepository;

    /** ProviderCategoryRepository */
    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;

    /** {@code PasswordEncoder} */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Handles the ContextRefreshedEvent by initializing default data.
     * @param  event - The application context event.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initializePeriods();
        initializeAdmin();
        initializeProviderCategories();
    }

    /**
     * Adds default periods to the database if they do not exist.
     */
    private void initializePeriods() {
        addPeriodIfNotExists("Yearly", 12);
        addPeriodIfNotExists("Half Yearly", 6);
        addPeriodIfNotExists("Three Month", 3);
    }

    /**
     * Adds a period with the specified name and months if it does not already exist.
     * @param name The name of the period (e.g., "Yearly").
     * @param months The number of months for the period.
     */
    private void addPeriodIfNotExists(String name, int months) {
        periodRepository.findByName(name)
                .orElseGet(() -> periodRepository.save(
                        Period.builder().name(name).months(months).build()
                ));
    }

    /**
     * Adds a default admin user if it does not already exist.
     */
    private void initializeAdmin() {
        String adminUsername = "admin";
        String adminEmail = "admin@example.com";
        String adminPassword = "admin123";

        if (userRepository.findByUserName(adminUsername) == null) {
            User admin = User.builder()
                    .userName(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role("SUPER_ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: " + adminUsername);
        }
    }

    /**
     * Adds default provider categories if they do not exist.
     */
    private void initializeProviderCategories() {
        addProviderCategoryIfNotExists("INTERNET");
        addProviderCategoryIfNotExists("TELEVISION");
        addProviderCategoryIfNotExists("CELLULAR");
    }

    /**
     * Adds a provider category with the specified name if it does not already exist.
     *
     * @param name - The name of the provider category.
     */
    private void addProviderCategoryIfNotExists(String name) {
        providerCategoryRepository.findByName(name)
                .orElseGet(() -> providerCategoryRepository.save(
                        ProviderCategory.builder().name(name).build()
                ));
    }
}
