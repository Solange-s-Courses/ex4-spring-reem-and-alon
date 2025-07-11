package com.example.ex4.components;

import com.example.ex4.dto.CartItemDTO;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ShoppingCart implements Serializable {
    private final List<CartItemDTO> items = new ArrayList<>();


    public ShoppingCart() {}

    public boolean addProduct(CartItemDTO item) {
        for (CartItemDTO existing : items) {
            if (Objects.equals(existing.getPkgId(), item.getPkgId()) &&
                    Objects.equals(existing.getSubPkgName(), item.getSubPkgName())) {
                return false;
            }
        }
        items.add(item);
        return true;
    }
    public boolean removeProduct(long pkgId, String subPkgName) {
        return items.removeIf(i -> i.getPkgId() == pkgId &&
                Objects.equals(i.getSubPkgName(), subPkgName));
    }

    public List<CartItemDTO> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clear() { items.clear(); }

    public Set<Long> getPkgIds() {
        return items.stream()
                .map(CartItemDTO::getPkgId)
                .collect(Collectors.toSet());
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(CartItemDTO::getMonthlyCost).sum();
    }
}
