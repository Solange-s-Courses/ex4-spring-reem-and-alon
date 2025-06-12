package com.example.ex4.service;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;

    public List<PlanPackage> getAllPackagesByCategory(String category, String sortByPrice) {
        return planPackageRepository.findPlanPackagesByProviderProfile_Category(category).orElse(null);
    }

    public List<PlanPackage> getAllProviderPackages(ProviderProfile profile) {
        return planPackageRepository.findAllByProviderProfile(profile).orElse(null);
    }

    public List<PlanPackage> getAllUserPackages() {

    }

    public void saveNewPackage(PlanPackage planPackage){
        planPackageRepository.save(planPackage);
    }
}
