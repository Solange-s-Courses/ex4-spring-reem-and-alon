package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.dto.PlanPackageOptionDTO;
import com.example.ex4.entity.Period;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.mappers.PlanPackageMapper;
import com.example.ex4.repository.PeriodRepository;
import com.example.ex4.repository.PlanPackageOptionRepository;
import com.example.ex4.repository.PlanPackageRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;
    @Autowired
    private PlanPackageOptionRepository planPackageOptionRepository;

    @Autowired
    private PlanPackageMapper planPackageMapper;
    @Autowired
    private PeriodRepository periodRepository;

    /**
     * Returns all plan packages (DTOs) for a provider profile.
     */
    public List<PlanPackageDTO> getAllProviderPackages(ProviderProfile profile) {
        List<PlanPackage> plans = planPackageRepository.findAllByProviderProfile(profile)
                .orElse(List.of());
        List<PlanPackageDTO> dtos = planPackageMapper.toDtoList(plans);
        for (int i = 0; i < plans.size(); i++) {
            dtos.get(i).setPlanOptions(planPackageMapper.toPeriodDtoList(plans.get(i).getPlanOptions()));
        }

        return dtos;
    }

    /**
     * Returns a single PlanPackageDTO for a PlanPackage entity.
     */
    public PlanPackageDTO getPlanPackage(PlanPackage planPackage) {
        PlanPackageDTO dto = planPackageMapper.toDto(planPackage);
        dto.setPlanOptions(planPackageMapper.toPeriodDtoList(planPackage.getPlanOptions()));
        return dto;
    }

    /**
     * Converts PlanPackageOption entities to DTOs (periods options).
     */
    private List<PlanPackageOptionDTO> getPeriodsOptions(List<PlanPackageOption> planOptions) {
        return planOptions.stream().map(option ->
                PlanPackageOptionDTO.builder()
                        .periodName(option.getPeriod().getName())
                        .months(option.getPeriod().getMonths())
                        .discount(option.getDiscount())
                        .build()
        ).toList();
    }

    /**
     * Saves a new plan package using the mapper.
     */
    @Transactional
    public void saveNewPackage(ProviderProfile providerProfile, PlanPackageDTO packageDTO) {

        PlanPackage newPlanPackage = PlanPackage.builder()
                .title(packageDTO.getTitle())
                .description(packageDTO.getDescription())
                .monthlyCost(packageDTO.getMonthlyCost()) // הוספת המחיר החודשי
                .expiryDate(packageDTO.getExpiryDate())
                .providerProfile(providerProfile)
                .build();

        // יצירת האופציות
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

        // הוספת האופציות לחבילה
        newPlanPackage.getPlanOptions().addAll(options);
        planPackageRepository.save(newPlanPackage);
    }

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
     */
    public List<PlanPackageDTO> findAllPlanPackageOptions(Set<Long> optionsId) {
        List<PlanPackageOption> planPeriods = planPackageOptionRepository.findAllById(optionsId);
        List<PlanPackage> plans = planPeriods.stream()
                .map(PlanPackageOption::getPlanPackage)
                .toList();
        return planPackageMapper.toDtoList(plans);
    }

    /**
     * Gets PlanPackageOption entities by their IDs.
     */
    public List<PlanPackageOption> getPlanOptionsByIds(Set<Long> optionsId) {
        return planPackageOptionRepository.findAllById(optionsId);
    }

    public boolean cartContainsSomePackageOption(Set<Long> pkgOptionIds, @NotNull Long newPkgOptionId) {
        if (pkgOptionIds.isEmpty()) return false;

        PlanPackageOption newOption = planPackageOptionRepository.findById(newPkgOptionId).orElse(null);
        if (newOption == null) return false;
        List<PlanPackageOption> cartOptions = planPackageOptionRepository.findAllById(pkgOptionIds);
        return cartOptions.stream()
                .anyMatch(option -> option.getPlanPackage().getId() == newOption.getPlanPackage().getId());
    }

    public List<PlanPackage> findAllProducts(Set<Long> optionsId) {
        List<PlanPackageOption> planOptions = getPlanOptionsByIds(optionsId);
        return planOptions.stream()
                .map(PlanPackageOption::getPlanPackage)
                .toList();
    }
}
