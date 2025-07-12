package com.example.ex4.service;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderCategoryRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing provider profiles and related business logic.
 * <p>
 * Handles creation, retrieval, update, and management of provider (admin) profiles.
 */
@Service
public class ProviderProfileService {

    /**
     * * Service for business logic of {@link User}.
     */
    @Autowired
    private UserService userService;

    /**
     * Repository for the {@code Entity} bean of {@link ProviderProfile}.
     */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    /**
     * Repository for the {@code Entity} bean of {@link ProviderCategory}.
     */
    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;


    public ProviderProfile findProviderProfile(User admin) {
        return providerProfileRepository.findByUser(admin).orElse(null);
    }

    public void activateAdminAccount(long id) {
        ProviderProfile profile = providerProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider profile not found"));
        profile.setApproved(true);
        providerProfileRepository.save(profile);
    }

    @Transactional
    public void registerProviderProfile(AdminRegistrationFormDTO profileForm) throws IOException {
        User admin = User.builder().userName(profileForm.getUserName())
                .email(profileForm.getEmail())
                .password(profileForm.getPassword())
                .role("ADMIN")
                .build();

        userService.addNewUser(admin);

        ProviderCategory category = providerCategoryRepository.findByName(profileForm.getCategory());

        ProviderProfile profile = ProviderProfile.builder()
                .companyName(profileForm.getCompanyName())
                .profileImage(profileForm.getImageFile().getBytes())
                .category(category)
                .user(admin).build();
        providerProfileRepository.save(profile);
    }

    public byte[] findProfileImage(Long id) {
        ProviderProfile profile = providerProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("No profile found"));
        return profile.getProfileImage();
    }

    public List<ProviderProfile> findAllPendingProfiles(){ return providerProfileRepository.findAllByApprovedFalse().orElse(new ArrayList<>()); }
    public void removeProviderProfile(long id) {providerProfileRepository.deleteById(id);}
}
