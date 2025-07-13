package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.dto.PlanPackageOptionDTO;
import com.example.ex4.entity.Period;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PeriodRepository;
import com.example.ex4.repository.PlanPackageOptionRepository;
import com.example.ex4.repository.PlanPackageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing plan packages, including mapping, validation, and calculations.
 *
 * @see PlanPackage
 * @see PlanPackageOption
 */
@Service
public class PlanPackageService {

    /** Repository for managing {@link PlanPackage} entities. */
    @Autowired
    private PlanPackageRepository planPackageRepository;

    /** Repository for managing {@link PlanPackageOption} entities. */
    @Autowired
    private PlanPackageOptionRepository planPackageOptionRepository;

    /** Repository for managing {@link Period} entities. */
    @Autowired
    private PeriodRepository periodRepository;

    /**
     * Returns all plan packages (DTOs) for a provider profile.
     *
     * @param profile the provider profile
     * @return list of plan packages as DTOs
     */
    public List<PlanPackageDTO> getAllProviderPackages(ProviderProfile profile) {
        List<PlanPackage> plans = planPackageRepository.findAllByProviderProfile(profile)
                .orElse(List.of());
        return plans.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new plan package for a provider (without mapper).
     *
     * @param providerProfile the provider
     * @param packageDTO the package details
     */
    @Transactional
    public void saveNewPackage(ProviderProfile providerProfile, PlanPackageDTO packageDTO) {
        PlanPackage newPlanPackage = PlanPackage.builder()
                .title(packageDTO.getTitle())
                .description(packageDTO.getDescription())
                .monthlyCost(packageDTO.getMonthlyCost())
                .expiryDate(packageDTO.getExpiryDate())
                .providerProfile(providerProfile)
                .build();

        List<PlanPackageOption> options = packageDTO.getPlanOptions().stream().map(option -> {
            Period period = periodRepository.findById(option.getPeriodId())
                    .orElseThrow(() -> new RuntimeException("Period not found: " + option.getPeriodId()));

            return PlanPackageOption.builder()
                    .planPackage(newPlanPackage)
                    .discount(option.getDiscount())
                    .period(period)
                    .optionPrice(calculateOptionPrice(packageDTO.getMonthlyCost(), option.getDiscount()))
                    .build();
        }).toList();

        newPlanPackage.getPlanOptions().addAll(options);
        planPackageRepository.save(newPlanPackage);
    }

    /**
     * Calculates the option price based on monthly cost and discount.
     *
     * @param monthlyCost the monthly base price
     * @param discount the discount
     * @return the calculated price for the option
     */
    public BigDecimal calculateOptionPrice(BigDecimal monthlyCost, BigDecimal discount) {
        if (monthlyCost == null) {
            return BigDecimal.ZERO;
        }
        if (discount == null || discount.compareTo(BigDecimal.ZERO) == 0) {
            return monthlyCost;
        }
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100)));
        return monthlyCost.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * Gets PlanPackageOption entities by their IDs.
     *
     * @param optionsId set of option IDs
     * @return list of options
     */
    public List<PlanPackageOption> getPlanOptionsByIds(Set<Long> optionsId) {
        return planPackageOptionRepository.findAllById(optionsId);
    }

    /**
     * Maps a PlanPackage entity to its DTO representation.
     *
     * @param entity the entity
     * @return the DTO
     */
    private PlanPackageDTO entityToDto(PlanPackage entity) {
        if (entity == null) {
            return null;
        }

        PlanPackageDTO dto = new PlanPackageDTO();
        dto.setTitle(entity.getTitle());
        dto.setPkgId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setMonthlyCost(entity.getMonthlyCost());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setProviderProfileId(entity.getProviderProfile().getId());
        dto.setPlanOptions(planOptionsToDto(entity.getPlanOptions()));

        return dto;
    }

    /**
     * Maps a list of PlanPackageOption entities to their DTOs.
     *
     * @param planOptions list of options
     * @return list of DTOs
     */
    private List<PlanPackageOptionDTO> planOptionsToDto(List<PlanPackageOption> planOptions) {
        if (planOptions == null) {
            return new ArrayList<>();
        }

        return planOptions.stream().map(option ->
                PlanPackageOptionDTO.builder()
                        .periodName(option.getPeriod().getName())
                        .months(option.getPeriod().getMonths())
                        .discount(option.getDiscount())
                        .periodId(option.getId())
                        .build()
        ).toList();
    }
}
