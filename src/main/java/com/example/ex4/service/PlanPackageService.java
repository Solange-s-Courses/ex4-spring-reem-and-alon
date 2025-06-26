package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.repository.ProviderCategoryRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;

    @Autowired
    private ProviderProfileService providerProfileService;

    @Transactional(readOnly = true)
    public List<PlanPackage> getAllPackagesByCategory(ProviderCategory providerCategory){
        return planPackageRepository.findAllByProviderProfile_Category(providerCategory);
    }

    public List<PlanPackage> getAllProviderPackages(ProviderProfile profile) {
        return planPackageRepository.findAllByProviderProfile(profile).orElse(null);
    }

    public void saveNewPackage(ProviderProfile providerProfile, PlanPackageDTO packageDTO) {
        PlanPackage newPackage = PlanPackage.builder()
                .packageType(packageDTO.getPackageType())
                .title(packageDTO.getTitle())
                .description(packageDTO.getDescription())
                .monthlyCost(packageDTO.getMonthlyCost())
                .expiryDate(packageDTO.getExpiryDate())
                .providerProfile(providerProfile)
                .build();

        planPackageRepository.save(newPackage);
    }

    public List<PlanPackage> findAllProducts(Set<Long> products) {
        return planPackageRepository.findAllById(products);
    }

}
