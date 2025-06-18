package com.example.ex4.service;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProviderProfileService {
    @Autowired
    private ProviderProfileRepository repository;
    @Autowired
    private UserService userService;

    public ProviderProfile findProviderProfile(AppUser admin) {
        return repository.findByAppUser(admin);
    }

    public void activateAdminAccount(long id) {
        ProviderProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Provider profile not found"));
        profile.setApproved(true);
        repository.save(profile);
    }

    @Transactional
    public void registerProviderProfile(AdminRegistrationFormDTO profileForm) throws IOException {
        AppUser admin = new AppUser(profileForm.getUserName(), profileForm.getEmail(), profileForm.getPassword());
        userService.addNewUser(admin, "ADMIN");

        ProviderProfile profile = new ProviderProfile();
        profile.setProfileImage(profileForm.getImageFile().getBytes());
        profile.setCategory(profileForm.getCategory());
        profile.setProviderName(profileForm.getProviderName());
        profile.setContactInfo(profileForm.getContactInfo());
        profile.setAppUser(admin);
        profile.setApproved(false);
        repository.save(profile);
    }

    public byte[] findProfileImage(Long id) {
        ProviderProfile profile = repository.findById(id).orElseThrow(()-> new RuntimeException("No profile found"));
        return profile.getProfileImage();
    }

    public List<ProviderProfile> findAllPendingProfiles(){ return repository.findAllByApprovedFalse().orElse(new ArrayList<>()); }
    public void removeProviderProfile(long id) {repository.deleteById(id);}
}
