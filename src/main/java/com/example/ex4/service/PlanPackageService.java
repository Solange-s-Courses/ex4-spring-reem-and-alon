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
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
    @Autowired
    private PlanPackageRepository planPackageRepository;
    @Autowired
    private PlanPackageOptionRepository planPackageOptionRepository;
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
     * Returns a single PlanPackageDTO for a PlanPackage entity.
     *
     * @param planPackage the plan package entity
     * @return the DTO representation
     */
    public PlanPackageDTO getPlanPackage(PlanPackage planPackage) {
        return entityToDto(planPackage);
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
                    .optionPrice(calculateOptionPrice(packageDTO.getMonthlyCost(), option))
                    .build();
        }).toList();

        newPlanPackage.getPlanOptions().addAll(options);
        planPackageRepository.save(newPlanPackage);
    }

    /**
     * Calculates the option price based on monthly cost and discount.
     *
     * @param monthlyCost the monthly base price
     * @param optionDTO the option DTO (period and discount)
     * @return the calculated price for the option
     */
    private BigDecimal calculateOptionPrice(BigDecimal monthlyCost, PlanPackageOptionDTO optionDTO) {
        if (monthlyCost == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalPrice = monthlyCost.multiply(BigDecimal.valueOf(optionDTO.getMonths()));

        if (optionDTO.getDiscount() != null) {
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                    optionDTO.getDiscount().divide(BigDecimal.valueOf(100))
            );
            totalPrice = totalPrice.multiply(discountMultiplier);
        }

        return totalPrice;
    }

    /**
     * Finds all PlanPackageDTOs for a set of PlanPackageOption IDs.
     *
     * @param optionsId set of plan package option IDs
     * @return list of corresponding package DTOs
     */
    public List<PlanPackageDTO> findAllPlanPackageOptions(Set<Long> optionsId) {
        List<PlanPackageOption> planPeriods = planPackageOptionRepository.findAllById(optionsId);
        List<PlanPackage> plans = planPeriods.stream()
                .map(PlanPackageOption::getPlanPackage)
                .toList();
        return plans.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
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
     * Checks if the cart already contains an option from the same package.
     *
     * @param pkgOptionIds the set of existing option IDs in the cart
     * @param newPkgOptionId the new option ID to check
     * @return true if same package already exists in the cart
     */
    public boolean cartContainsSomePackageOption(Set<Long> pkgOptionIds, @NotNull Long newPkgOptionId) {
        if (pkgOptionIds.isEmpty()) return false;

        PlanPackageOption newOption = planPackageOptionRepository.findById(newPkgOptionId).orElse(null);
        if (newOption == null) return false;
        List<PlanPackageOption> cartOptions = planPackageOptionRepository.findAllById(pkgOptionIds);
        return cartOptions.stream()
                .anyMatch(option -> option.getPlanPackage().getId() == newOption.getPlanPackage().getId());
    }

    /**
     * Finds all products (PlanPackage) for a set of option IDs.
     *
     * @param optionsId set of option IDs
     * @return list of packages for those options
     */
    public List<PlanPackage> findAllProducts(Set<Long> optionsId) {
        List<PlanPackageOption> planOptions = getPlanOptionsByIds(optionsId);
        return planOptions.stream()
                .map(PlanPackageOption::getPlanPackage)
                .toList();
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
