package org.inventorymgmt.util;


import org.inventorymgmt.model.Product;

import java.util.function.Predicate;

public class PriceRangeFilter {
    public static Predicate<Product> byPriceRange(double minPrice, double maxPrice) {
        return product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice;
    }
}
