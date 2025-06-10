package com.example.ex4.service;

import com.example.ex4.dto.BusinessCardFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.BusinessCard;
import com.example.ex4.repository.BusinessCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BusinessCardService {
    @Autowired
    private BusinessCardRepository repository;

    public BusinessCard findAdminBusinessCard(AppUser admin) {
        return repository.findByAppUser(admin).orElse(null);
    }

    public void saveAdminProfile(AppUser admin, BusinessCardFormDTO profile) throws IOException {
        BusinessCard card = new BusinessCard();
        card.setProfileImage(profile.getImageFile().getBytes());
        card.setCategory(profile.getCategory());
        card.setAboutMe(profile.getAboutMe());
        card.setCity(profile.getCity());
        card.setAppUser(admin);
        repository.save(card);
    }

    public byte[] findProfileImage(long id) {
        BusinessCard adminBusinessCard = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Business card not found"));
        return adminBusinessCard.getProfileImage();
    }

    public List<BusinessCard> getAllBusinessResults(String category, String city,String sortByPrice) {
        List<BusinessCard> results= repository.findAllByCategoryAndCity(category,city).orElse(null);
        results.stream().sorted().forEach(result->
        })
        return
    }
}
