package com.example.ex4.repository;

import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.BusinessCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCardRepository extends JpaRepository<BusinessCard, Long> {
    Optional<BusinessCard> findByAppUser(AppUser appUser);

}

