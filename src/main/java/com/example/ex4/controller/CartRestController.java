package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private ShoppingCart shoppingCart;
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO item, @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        if (subscriptionService.existsInUserSubscription(userPrincipal.getUser(), item)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already subscribed to that item");
        }
        if (!shoppingCart.addProduct(item)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already exists in the cart!");
        }
        return ResponseEntity.accepted().body("Package added to cart successfully!");
    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody CartItemDTO item) {
        if (!shoppingCart.removeProduct(item.getPkgId(),item.getSubPkgName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product does not exists in the cart!");
        }
        return ResponseEntity.ok().body("Package removed from cart successfully!");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }
}
