package com.example.ex4.components;
import com.example.ex4.entity.Cart;
import com.example.ex4.repository.CartRepository;
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

@Component
public class CartInterceptor implements HandlerInterceptor {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShoppingCart shoppingCart;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (principal != null && shoppingCart.getItems().isEmpty()) {
            Cart savedCart = cartRepository.findByUser_UserName(principal.getName());
            if (savedCart != null && savedCart.getPackages() != null) {
                shoppingCart.setItems(new ArrayList<>(savedCart.getPackages()));
            }
        }
        return true;
    }
}

