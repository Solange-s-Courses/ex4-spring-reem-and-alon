package com.example.ex4.repository;

import com.example.ex4.entity.ProviderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link ProviderCategory} entities.
 */
public interface ProviderCategoryRepository extends JpaRepository<ProviderCategory, Long> {

    /**
     * Finds a category by its name.
     *
     * @param category the category name
     * @return an optional containing the found category, if exists
     */
    Optional<ProviderCategory> findByName(String category);

    /**
     * Checks if a category with the given name exists.
     *
     * @param categoryName the category name
     * @return true if exists, false otherwise
     */
    boolean existsByName(String categoryName);
}
