package org.inventorymgmt.util;

import org.inventorymgmt.model.Product;

import java.util.function.Predicate;

public class CategoryFilter {
    public static Predicate<Product> byCategory(Class<? extends Product> category) {
        return category::isInstance;
    }
}
