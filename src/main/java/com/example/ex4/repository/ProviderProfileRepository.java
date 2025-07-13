package com.example.ex4.repository;

import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link ProviderProfile} entities.
 */
@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {

    /**
     * Finds a provider profile by the associated user.
     *
     * @param appUser the user entity
     * @return an optional containing the provider profile, if found
     */
    Optional<ProviderProfile> findByUser(User appUser);

    /**
     * Finds all provider profiles that are not yet approved.
     *
     * @return optional containing the list of unapproved provider profiles
     */
    Optional<List<ProviderProfile>> findAllByApprovedFalse();

    /**
     * Finds all provider profiles in a category that have at least one plan/package.
     *
     * @param category the provider category
     * @return list of provider profiles with non-empty plan packages in the given category
     */
    List<ProviderProfile> findByCategoryAndPlanPackagesIsNotEmpty(ProviderCategory category);

    Optional<ProviderProfile> findByCompanyName(String companyName);
}
