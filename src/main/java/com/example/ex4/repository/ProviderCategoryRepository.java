package com.example.ex4.repository;

import com.example.ex4.entity.ProviderCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderCategoryRepository extends JpaRepository<ProviderCategory, Long> {
    List<ProviderCategory> findAllByName(String name);

    ProviderCategory findByName(String category);

    boolean existsByName(String categoryName);
}
