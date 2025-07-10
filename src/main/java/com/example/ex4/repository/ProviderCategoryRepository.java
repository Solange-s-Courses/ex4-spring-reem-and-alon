package com.example.ex4.repository;

import com.example.ex4.entity.ProviderCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderCategoryRepository extends JpaRepository<ProviderCategory, Long> {

    ProviderCategory findByName(String category);

    boolean existsByName(String categoryName);
}
