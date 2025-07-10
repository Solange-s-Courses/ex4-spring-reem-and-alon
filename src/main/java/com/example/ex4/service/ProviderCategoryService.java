package com.example.ex4.service;

import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.repository.ProviderCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderCategoryService {

    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;

    public List<String> findAllCategoryNames() {
        return providerCategoryRepository.findAll().stream().map(ProviderCategory::getName).collect(Collectors.toList());
    }

    public ProviderCategory findByName(String categoryName) {
        return providerCategoryRepository.findByName(categoryName);
    }

    public boolean categoryExists(String categoryName) {
        return providerCategoryRepository.existsByName(categoryName);
    }

    public void addCategory(String categoryName) {
        providerCategoryRepository.save(ProviderCategory.builder().name(categoryName).build());
    }
}
