package com.example.ex4.service;

import com.example.ex4.constants.ErrorMsg;
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
 * Handles creation, retrieval, approval, image fetching, and removal of provider (admin) profiles.
 *
 * @see ProviderProfile
 * @see ProviderCategory
 * @see User
 */
@Service
public class ProviderProfileService {

    /**
     * Service for user business logic.
     */
    @Autowired
    private UserService userService;

    /**
     * Repository for provider profiles.
     */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    /**
     * Repository for provider categories.
     */
    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;

    /**
     * Finds a provider profile by its associated user.
     *
     * @param admin the user entity (admin/provider)
     * @return the provider profile
     * @throws RuntimeException if no profile found for the user
     */
    public ProviderProfile findProviderProfile(User admin) {
        return providerProfileRepository.findByUser(admin)
                .orElseThrow(() -> new RuntimeException(ErrorMsg.PROFILE_NOT_FOUND));
    }

    /**
     * Approves (activates) an admin/provider profile by ID.
     *
     * @param id the profile ID
     * @throws RuntimeException if the profile is not found
     */
    public void activateAdminAccount(long id) {
        ProviderProfile profile = providerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMsg.PROFILE_NOT_FOUND));
        profile.setApproved(true);
        providerProfileRepository.save(profile);
    }

    /**
     * Registers a new provider profile using registration form data.
     * Creates both the user and the provider profile.
     *
     * @param profileForm the admin registration form data
     * @throws IOException if there is an error processing the image
     * @throws RuntimeException if the category does not exist
     */
    @Transactional
    public void registerProviderProfile(AdminRegistrationFormDTO profileForm) throws IOException {
        User admin = User.builder()
                .userName(profileForm.getUserName())
                .email(profileForm.getEmail())
                .password(profileForm.getPassword())
                .role("ADMIN")
                .build();

        userService.addNewUser(admin);

        ProviderCategory category = providerCategoryRepository.findByName(profileForm.getCategory())
                .orElseThrow(() -> new RuntimeException(ErrorMsg.PROVIDER_CATEGORY_NOT_FOUND));

        ProviderProfile profile = ProviderProfile.builder()
                .companyName(profileForm.getCompanyName())
                .profileImage(profileForm.getImageFile().getBytes())
                .category(category)
                .user(admin).build();
        providerProfileRepository.save(profile);
    }

    /**
     * Returns the profile image bytes for a given provider profile ID.
     *
     * @param id the profile ID
     * @return byte array of the profile image
     * @throws RuntimeException if profile not found
     */
    public byte[] findProfileImage(Long id) {
        ProviderProfile profile = providerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorMsg.PROFILE_NOT_FOUND));
        return profile.getProfileImage();
    }

    /**
     * Finds all provider profiles that are pending approval.
     *
     * @return list of unapproved provider profiles (may be empty)
     */
    public List<ProviderProfile> findAllPendingProfiles() {
        return providerProfileRepository.findAllByApprovedFalse().orElse(new ArrayList<>());
    }

    /**
     * Removes a provider profile by ID.
     *
     * @param id the provider profile ID
     */
    public void removeProviderProfile(long id) {
        providerProfileRepository.deleteById(id);
    }
}

