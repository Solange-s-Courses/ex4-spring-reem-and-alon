package com.example.ex4.service;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.repository.PlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanPackageService {
    @Autowired
    private PlanPackageRepository planPackageRepository;

    public List<PlanPackage> getAllPackages(AppUser user) {
        return planPackageRepository.findAllByAppUser(user).orElse(null);
    }
    public void saveNewPackage(AppUser admin,PlanPackage planPackage){
        planPackage.setAppUser(admin);
        planPackageRepository.save(planPackage);
    }


}
