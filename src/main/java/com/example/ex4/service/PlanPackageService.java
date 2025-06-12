package com.example.ex4.service;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    public List<PlanPackage> getAllPackagesByCategory(String category) {
        return planPackageRepository.findPlanPackagesByProviderProfile_Category(category).orElse(null);
    }

    public List<PlanPackage> getAllProviderPackages(ProviderProfile profile) {
        return planPackageRepository.findAllByProviderProfile(profile).orElse(null);
    }

    @Transactional
    public void saveNewPackage(String username,PlanPackageDTO planPackage){
        ProviderProfile providerProfile = providerProfileRepository.findProviderProfileByAppUser_UserName(username).orElse(null);
        PlanPackage newPackage = new PlanPackage();
        newPackage.setProviderProfile(providerProfile);
        newPackage.setPackageType(planPackage.packageType);
        newPackage.setPrice(planPackage.price);
        newPackage.setDescription(planPackage.description);
        newPackage.setTitle(planPackage.title);
        planPackageRepository.save(newPackage);
    }
}
