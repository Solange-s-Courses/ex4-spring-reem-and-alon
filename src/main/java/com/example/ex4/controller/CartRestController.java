package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.entity.PlanPackageOption;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private ShoppingCart shoppingCart;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PlanPackageService planPackageService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody @Valid CartItemDTO item, @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        if (subscriptionService.existsInUserSubscription(userPrincipal.getUser(), item)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already subscribed to that item");
        }
        if (!shoppingCart.addProduct(item)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already exists in the cart!");
        }
        return ResponseEntity.accepted().body("Package added to cart successfully!");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody @Valid CartItemDTO item) {
        if (!shoppingCart.removeProduct(item.getPkgOptionId(),item.getSubPkgName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product does not exists in the cart!");
        }
        return ResponseEntity.ok().body("Package removed from cart successfully!");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidRequest1(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }
}
