package org.inventorymgmt.service;



import org.inventorymgmt.model.Product;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InventoryService<T extends Product> {
    private final Map<String, T> inventory = new HashMap<>();

    public void addProduct(T product) {
        inventory.put(product.getId(), product);
    }

    public Optional<T> getProductById(String id) {
        return Optional.ofNullable(inventory.get(id));
    }

    public List<T> filterProducts(Predicate<T> condition) {
        return inventory.values().stream()
                .filter(condition)
                .collect(Collectors.toList());
    }


    public void applyDiscount(Predicate<T> condition, double discountPercentage) {
        inventory.values().stream()
                .filter(condition)
                .forEach(product -> {
                    double discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
                    System.out.println(product.getName() + " discounted to: " + discountedPrice);
                });
    }
}
