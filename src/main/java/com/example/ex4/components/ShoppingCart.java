package com.example.ex4.components;

import com.example.ex4.entity.PlanPackage;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ShoppingCart implements Serializable {
    private List<PlanPackage> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }
    public boolean addProduct(PlanPackage product) {
        if (products.stream().anyMatch(p -> p.getId() == product.getId())) {
            return false;
        }
        products.add(product);
        return true;
    }
    public void removeProduct(PlanPackage product) { products.remove(product); }
    public List<PlanPackage> getProducts() { return List.copyOf(products); }
    public void clear() { products.clear(); }
}
