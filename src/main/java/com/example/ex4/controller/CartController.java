package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.constants.PlanPackageTypes;
import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.PlanPackageRepository;
import com.example.ex4.service.CartService;
import com.example.ex4.service.PlanPackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @Autowired
    private PlanPackageService planPackageService;




    @PostMapping("/add")
    public ResponseEntity<String> addNewPackage(@RequestParam Long pkgId, ShoppingCart sessionCart, Principal principal) {
        if (sessionCart.findPackage(pkgId) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Package already exists");
        }
        PlanPackage savedPackage = service.addToCart(principal.getName(), pkgId);
        sessionCart.addPackage(savedPackage);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Package added successfully");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }

}
