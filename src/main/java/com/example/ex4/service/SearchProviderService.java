package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.dto.SearchResultDTO;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchProviderService {

    @Autowired
    private ProviderProfileService providerProfileService;

    @Autowired
    private ProviderCategoryService providerCategoryService;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PlanPackageService planPackageService;

    public List<SearchResultDTO> findAllProviderResultByCategory(String category) {
        ProviderCategory providerCategory = providerCategoryService.findByName(category);
        List<ProviderProfile> providers = providerProfileRepository.findByCategoryAndPlanPackagesIsNotEmpty(providerCategory);

        return providers.stream().map(provider -> {
            double avgStars = reviewRepository.findAvgStarsByProviderProfile(provider).orElse(0.0);
            long reviewCount = reviewRepository.countAllByProviderProfile(provider);
            List<PlanPackageDTO> plans = planPackageService.getAllProviderPackages(provider);

            return SearchResultDTO.builder()
                    .avgStars(avgStars)
                    .reviewCount(reviewCount)
                    .providerName(provider.getCompanyName())
                    .plans(plans)
                    .build();
        }).toList();
    }
}
