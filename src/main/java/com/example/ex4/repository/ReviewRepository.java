package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByProviderProfile(ProviderProfile providerProfile);

    List<Review> findReviewsByProviderProfile_Id(long providerProfileId);
    @Query("SELECT AVG(r.stars) FROM Review r WHERE r.providerProfile = :providerProfile")
    Double findAvgStarsByProviderProfile(@Param("providerProfile") ProviderProfile providerProfile);
    List<Review> findAllByProviderProfile_Id(Long providerProfileId);
    List<Review> findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(long providerProfile_id, double stars);
    Long countAllByProviderProfile(ProviderProfile providerProfile);
}
