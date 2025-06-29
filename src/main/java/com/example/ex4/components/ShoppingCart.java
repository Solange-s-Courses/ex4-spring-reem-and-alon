package com.example.ex4.components;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
public class ShoppingCart implements Serializable {
    private Set<Long> productsIds = new HashSet<>();


    public ShoppingCart() {}

    public boolean addProduct(long productId) {
        if (productsIds.stream().anyMatch( pid-> pid == productId)) {
            return false;
        }
        productsIds.add(productId);
        return true;
    }
    public boolean removeProduct(long productId) {
        return productsIds.removeIf( pid-> pid == productId);
    }

    public Set<Long> getProducts() { return productsIds; }

    public void clear() { productsIds.clear(); }

    public Integer getProductsAmount() {
        return productsIds.size();
    }
}
