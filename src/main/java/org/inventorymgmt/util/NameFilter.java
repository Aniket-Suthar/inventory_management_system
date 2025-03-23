package org.inventorymgmt.util;


import org.inventorymgmt.model.Product;

import java.util.function.Predicate;

public class NameFilter {
    public static Predicate<Product> byNameContains(String keyword) {
        return product -> product.getName().toLowerCase().contains(keyword.toLowerCase());
    }
}
