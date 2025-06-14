package com.example.ex4.components;
import com.example.ex4.entity.Cart;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.repository.CartRepository;
import com.example.ex4.service.CartService;
import com.example.ex4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartInterceptor implements HandlerInterceptor {

    @Autowired
    private CartService cartService;

    @Autowired
    private ShoppingCart shoppingCart;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Principal principal = request.getUserPrincipal();

        System.out.println("=== CartInterceptor Debug ===");
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("Principal exists: " + (principal != null));
        System.out.println("Cart items count: " + shoppingCart.getItems().size());
        System.out.println("Cart is empty: " + shoppingCart.getItems().isEmpty());

        if (principal != null) {
            System.out.println("Loading cart from DB for user: " + principal.getName());
            List<PlanPackage> dbItems = cartService.getUserCartPackages(principal.getName());
            System.out.println("Found " + dbItems.size() + " items in DB");
            shoppingCart.setItems(dbItems);
            System.out.println("Cart after loading: " + shoppingCart.getItems().size());
        }

        return true;
    }


}

