package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.repository.PlanPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("user/cart")
public class CartController {

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @Autowired
    private PlanPackageRepository PlanPackageRepository;

    @GetMapping
    public String addToCart(Model model) {
        model.addAttribute("shoppingCart", sessionCart.getProducts());
        return "user/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long pkgId, RedirectAttributes redirectAttributes) {
        sessionCart.addProduct(PlanPackageRepository.findPlanPackagesById(pkgId));
        redirectAttributes.addFlashAttribute("successMessage", "Package has been saved to cart!");
        return "redirect:/user/search-providers";
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
    }
}
