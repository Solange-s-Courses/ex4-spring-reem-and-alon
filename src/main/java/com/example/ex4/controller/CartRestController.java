package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.repository.PlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private ShoppingCart sessionCart;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Map<String,Object> data) {
        long pkgId = Long.parseLong(data.get("pkgId").toString());
        if (!sessionCart.addProduct(pkgId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already exists in the cart!");
        }
        return ResponseEntity.accepted().body("Package added to cart successfully!");
    }
    @DeleteMapping("/remove/{pkgId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long pkgId) {
        if (!sessionCart.removeProduct(pkgId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product does not exists in the cart!");
        }
        return ResponseEntity.ok().body("Package removed from cart successfully!");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }
}
