package com.example.ex4.components;

import com.example.ex4.entity.PlanPackage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@SessionScope
public class ShoppingCart implements Serializable {

    private List<PlanPackage> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<PlanPackage> items) {
        this.items = items;
    }

    public void removeItem(PlanPackage item) {
        items.remove(item);
    }

    public void addItem(PlanPackage item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public List<PlanPackage> getItems() {
        return items;
    }

    public void addPackage(PlanPackage newPackage) {
        items.add(newPackage);
    }

    public PlanPackage findPackage(Long pkgId) {
        return items.stream()
                .filter(pkg -> Objects.equals(pkg.getId(), pkgId))
                .findFirst()
                .orElse(null);
    }
}
