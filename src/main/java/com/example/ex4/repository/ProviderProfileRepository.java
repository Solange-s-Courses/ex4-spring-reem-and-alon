package com.example.ex4.repository;

import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
    Optional<ProviderProfile> findByUser(User appUser);
    Optional<List<ProviderProfile>> findAllByApprovedFalse();
}

