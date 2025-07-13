package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.ShoppingCart;
import com.example.ex4.dto.CartItemDTO;
import com.example.ex4.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * RestController for handling shopping cart actions for the user.
 * Provides endpoints to add and remove package options to/from the user's shopping cart.
 */
@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    /** Success message for adding product */
    private static final String PRODUCT_ADDED_SUCCESS = "Package added to cart successfully!";
    /** Message if product already exists in cart */
    private static final String PRODUCT_ALREADY_EXISTS = "Product already exists in the cart!";
    /** Message if user already subscribed to product */
    private static final String ALREADY_SUBSCRIBED = "Already subscribed to that item";
    /** Success message for removing product */
    private static final String PRODUCT_REMOVED_SUCCESS = "Package removed from cart successfully!";
    /** Message if product does not exist in cart */
    private static final String PRODUCT_NOT_EXISTS = "Product does not exists in the cart!";
    /** Message for invalid request */
    private static final String INVALID_REQUEST = "Invalid request: ";

    /** Shopping cart of the user (session bean) */
    @Autowired
    private ShoppingCart shoppingCart;

    /** Service for Subscription business logic */
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Adds a new "plan package option" to the user's shopping cart.
     *
     * @param item the package option the user wants to add
     * @param userPrincipal the authenticated user
     * @return response entity with appropriate status and message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody @Valid CartItemDTO item,
                                            @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        if (subscriptionService.existsInUserSubscription(userPrincipal.getUser(), item)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ALREADY_SUBSCRIBED);
        }
        if (!shoppingCart.addProduct(item)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_ALREADY_EXISTS);
        }
        return ResponseEntity.accepted().body(PRODUCT_ADDED_SUCCESS);
    }

    /**
     * Removes a product from the user's shopping cart.
     *
     * @param item the package option to remove
     * @return response entity with appropriate status and message
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody @Valid CartItemDTO item) {
        if (!shoppingCart.removeProduct(item.getPkgOptionId(), item.getSubPkgName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PRODUCT_NOT_EXISTS);
        }
        return ResponseEntity.ok().body(PRODUCT_REMOVED_SUCCESS);
    }

    /**
     * Handles invalid request parameter types.
     *
     * @param ex the exception
     * @return response entity with error message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleInvalidRequest1(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INVALID_REQUEST + ex.getMessage());
    }

    /**
     * Handles general exceptions.
     *
     * @param ex the exception
     * @return response entity with error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INVALID_REQUEST + ex.getMessage());
    }
}
