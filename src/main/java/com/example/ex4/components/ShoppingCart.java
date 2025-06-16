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
    private List<Long> productsIds;

    public ShoppingCart() {
        productsIds = new ArrayList<>();
    }
    public boolean addProduct(long productId) {
        if (productsIds.stream().anyMatch( pid-> pid == productId)) {
            return false;
        }
        productsIds.add(productId);
        return true;
    }
    public boolean removeProduct(long productId) {
        if (productsIds.stream().anyMatch( pid-> pid == productId)) {
            productsIds.remove(productId);
            return true;
        }
        return false;
    }
    public List<Long> getProducts() { return List.copyOf(productsIds); }
    public void clear() { productsIds.clear(); }
}
