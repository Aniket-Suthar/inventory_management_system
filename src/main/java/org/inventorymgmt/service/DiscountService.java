package org.inventorymgmt.service;


import org.inventorymgmt.model.Product;

@FunctionalInterface
public interface DiscountService<T extends Product> {
    void apply(T product);
}
