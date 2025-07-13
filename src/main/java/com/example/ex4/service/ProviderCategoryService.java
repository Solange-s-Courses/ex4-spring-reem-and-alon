package com.example.ex4.service;

import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.repository.ProviderCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for provider categories: manage, find, add, check existence.
 *
 * @see ProviderCategory
 */
@Service
public class ProviderCategoryService {

    /** Repository for managing {@code PlanPackage} entities. */
    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;

    /**
     * Returns all category names.
     *
     * @return list of category names
     */
    public List<String> findAllCategoryNames() {
        return providerCategoryRepository.findAll().stream().map(ProviderCategory::getName).collect(Collectors.toList());
    }

    /**
     * Finds a category by name.
     *
     * @param categoryName the category name
     * @return the ProviderCategory or null if not found
     */
    public ProviderCategory findByName(String categoryName) {
        return providerCategoryRepository.findByName(categoryName).orElse(null);
    }

    /**
     * Checks if a category exists by name.
     *
     * @param categoryName the category name
     * @return true if exists, false otherwise
     */
    public boolean categoryExists(String categoryName) {
        return providerCategoryRepository.existsByName(categoryName);
    }

    /**
     * Adds a new provider category.
     *
     * @param categoryName the category name to add
     */
    public void addCategory(String categoryName) {
        providerCategoryRepository.save(ProviderCategory.builder().name(categoryName).build());
    }
}

