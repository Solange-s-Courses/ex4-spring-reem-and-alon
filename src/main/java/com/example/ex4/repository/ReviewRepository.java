package com.example.ex4.repository;

import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.stars) FROM Review r WHERE r.providerProfile = :providerProfile")
    Optional<Double> findAvgStarsByProviderProfile(@Param("providerProfile") ProviderProfile providerProfile);
    List<Review> findAllByProviderProfile_Id(Long providerProfileId);
    List<Review> findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(long providerProfile_id, double stars);
    Long countAllByProviderProfile(ProviderProfile providerProfile);
}
