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

/**
 * Service for searching providers by category and aggregating their data (plans, reviews).
 *
 * @see ProviderCategory
 * @see ProviderProfile
 * @see SearchResultDTO
 * @see PlanPackageService
 */
@Service
public class SearchProviderService {

    /** Service for provider categories. */
    @Autowired
    private ProviderCategoryService providerCategoryService;

    /** Repository for provider profiles. */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    /** Repository for reviews. */
    @Autowired
    private ReviewRepository reviewRepository;

    /** Service for plan package business logic. */
    @Autowired
    private PlanPackageService planPackageService;

    /**
     * Returns a list of search results for providers in a given category,
     * each including average stars, review count, and available plans.
     *
     * @param category the provider category name
     * @return list of search result DTOs for each provider in the category
     */
    public List<SearchResultDTO> findAllProviderResultByCategory(String category) {
        List<ProviderProfile> providers;
        if (category != null && !category.equals("All Categories")) {
            ProviderCategory providerCategory = providerCategoryService.findByName(category);
            providers = providerProfileRepository.findByCategoryAndPlanPackagesIsNotEmpty(providerCategory);
        }
        else{
            providers=providerProfileRepository.findByPlanPackagesIsNotEmpty();
        }
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

