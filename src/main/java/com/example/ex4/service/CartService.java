package com.example.ex4.service;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.Cart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.repository.CartRepository;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository repository;

    @Autowired
    private PlanPackageRepository planPackageRepository;

    @Transactional
    public PlanPackage addToCart(String username, Long newPackageId) {
        PlanPackage planPackage = planPackageRepository.findById(newPackageId)
                .orElseThrow(() -> new IllegalArgumentException("Package does not exist"));
        AppUser user = userRepository.findByUserName(username);

        Cart userCart = repository.findCartByUser(user);
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
        }
        boolean exists = userCart.getPackages().stream()
                .anyMatch(pkg -> pkg.getId() == planPackage.getId());
        if (!exists) {
            userCart.getPackages().add(planPackage);
            repository.save(userCart); // <-- **קריטי!** כאן מתבצע עדכון הטבלה המקשרת

            System.out.println("After save, cart ID: " + userCart.getId());
        }

        return planPackage;
    }

    @Transactional(readOnly = true)
    public List<PlanPackage> getUserCartPackages(String username) {
        Cart cart = repository.findByUser_UserName(username);
        return (cart != null) ? new ArrayList<>(cart.getPackages()) : new ArrayList<>();
    }
}
