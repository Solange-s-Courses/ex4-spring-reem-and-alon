package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PlanPackageRepository;
import java.util.LinkedHashMap;

import com.example.ex4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;

    @Autowired
    private ProviderProfileService providerProfileService;

    @Autowired
    private ReviewService reviewService;

    @Transactional(readOnly = true)
    public List<PlanPackage> getAllPackagesByCategory(ProviderCategory providerCategory){
        List<ProviderProfile> profiles = providerProfileService.findAllProvidersByCategory(providerCategory);

        return planPackageRepository.findAllByProviderProfileIn(profiles);
    }

    public List<PlanPackage> getAllProviderPackages(ProviderProfile profile) {
        return planPackageRepository.findAllByProviderProfile(profile).orElse(null);
    }

    public void saveNewPackage(ProviderProfile providerProfile, PlanPackageDTO packageDTO) {
        PlanPackage newPackage = PlanPackage.builder()
                .halfYearlyDiscount(packageDTO.getHalfYearlyDiscount())
                .yearlyDiscount(packageDTO.getYearlyDiscount())
                .threeMonthDiscount(packageDTO.getThreeMonthDiscount())
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
    
/*    public Map<Long, List<PlanPackage>> groupPackagesWithReviewStats(List<PlanPackage> packages) {

        Map<Long, List<PlanPackage>> grouped =
                packages.stream().collect(Collectors.groupingBy(
                        p -> p.getProviderProfile().getId(),
                        LinkedHashMap::new,
                        Collectors.toList()));

        grouped.forEach((providerId, pkgList) -> {
            long reviewersCount = reviewService.getReviewersCount(providerId);
            double starsAvg   = reviewService.getAverageStars(providerId);
            double roundedAvg = Math.round(starsAvg * 10) / 10.0;

            pkgList.forEach(p -> {
                p.setReviewCount(reviewersCount);
                p.setAvgStars(roundedAvg);
            });
        });

        return grouped;
    }*/
}

