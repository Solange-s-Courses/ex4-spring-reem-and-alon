package com.example.ex4.controller;

import com.example.ex4.components.ShoppingCart;
import com.example.ex4.service.PlanPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user/cart")
public class CartPageController {

    @Autowired
    @Qualifier("sessionBeanCart")
    private ShoppingCart sessionCart;

    @Autowired
    private PlanPackageService planPackageService;

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("shoppingCart", planPackageService.findAllProducts(sessionCart.getProducts()));
        return "user/cart";
    }
}
