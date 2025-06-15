package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.repository.PlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @Autowired
    private PlanPackageRepository PlanPackageRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addNewPackage(@RequestParam Long pkgId, Principal principal) {
        sessionCart.addProduct(PlanPackageRepository.findPlanPackagesById(pkgId));
        return ResponseEntity.accepted().body("Package have been saved to cart");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }
}
