package com.example.ex4.repository;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByProviderProfile(ProviderProfile providerProfile);

    List<Review> findReviewsByProviderProfile_Id(long providerProfileId);

    List<Review> findAllByProviderProfile_Id(Long providerProfileId);
    List<Review> findAllByProviderProfileIdAndStarsOrderByCreatedAtDesc(long providerProfile_id, double stars);
}
