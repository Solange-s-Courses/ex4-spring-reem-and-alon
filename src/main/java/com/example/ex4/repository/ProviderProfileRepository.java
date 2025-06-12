package com.example.ex4.repository;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
    ProviderProfile findByAppUser(AppUser appUser);
    Optional<ProviderProfile> findProviderProfileByAppUser_UserName(String username);
}

