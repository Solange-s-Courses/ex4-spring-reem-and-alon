package com.example.ex4.components;

import com.example.ex4.dto.CartItemDTO;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the shopping cart for the current user session.
 * <p>
 * This component stores a list of {@link CartItemDTO} objects representing the items in the cart.
 * Provides methods for adding, removing, listing, and clearing cart items, as well as calculating the total cost.
 * <p>
 */
@Component
public class ShoppingCart implements Serializable {

    /**
     * The list of items currently in the shopping cart.
     */
    private final List<CartItemDTO> items = new ArrayList<>();

    /**
     * Constructs a new, empty shopping cart.
     */
    public ShoppingCart() {}

    /**
     * Adds a product to the shopping cart if it does not already exist by package ID.
     *
     * @param item the cart item to add
     * @return {@code true} if the item was added; {@code false} if an item with the same package ID already exists
     */
    public boolean addProduct(CartItemDTO item) {
        for (CartItemDTO existing : items) {
            if (Objects.equals(existing.getPkgId(), item.getPkgId())) {
                return false;
            }
        }
        items.add(item);
        return true;
    }

    /**
     * Removes a product from the cart by package ID and subscription package name.
     *
     * @param pkgId  the package ID of the product to remove
     * @param subPkgName the subscription package name of the product to remove
     * @return {@code true} if an item was removed; {@code false} otherwise
     */
    public boolean removeProduct(long pkgId, String subPkgName) {
        return items.removeIf(i -> i.getPkgId() == pkgId &&
                Objects.equals(i.getSubPkgName(), subPkgName));
    }

    /**
     * Returns an unmodifiable list of all items currently in the cart.
     *
     * @return the list of cart items
     */
    public List<CartItemDTO> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Clears all items from the shopping cart.
     */
    public void clear() { items.clear(); }

    /**
     * Gets the set of all package IDs present in the cart.
     * @return a set of package IDs
     */
    public Set<Long> getPkgIds() {
        return items.stream()
                .map(CartItemDTO::getPkgId)
                .collect(Collectors.toSet());
    }

    /**
     * Calculates the total monthly cost of all items in the cart.
     *
     * @return the total cost as a double
     */
    public double getTotalCost() {
        return items.stream().mapToDouble(CartItemDTO::getMonthlyCost).sum();
    }
}
