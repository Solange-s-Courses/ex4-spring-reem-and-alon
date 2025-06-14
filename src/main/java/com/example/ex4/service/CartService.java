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
    public PlanPackage addToCart(String username, Long newPackage) {
        PlanPackage planPackage = planPackageRepository.findById(newPackage).orElse(null);
        AppUser user = userRepository.findByUserName(username);
        Cart userCart = repository.findCartByUser(user);

        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
        }
        userCart.addPackage(planPackage);
        repository.save(userCart);
        return planPackage;
    }

    @Transactional(readOnly = true)
    public List<PlanPackage> getUserCartPackages(String username) {
        Cart cart = repository.findByUser_UserName(username);
        return (cart != null) ? new ArrayList<>(cart.getPackages()) : new ArrayList<>();
    }
}
