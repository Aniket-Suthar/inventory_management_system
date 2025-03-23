package org.inventorymgmt;

import org.inventorymgmt.model.Clothing;
import org.inventorymgmt.model.Electronics;
import org.inventorymgmt.model.Grocery;
import org.inventorymgmt.model.Product;
import org.inventorymgmt.service.InventoryService;
import org.inventorymgmt.util.CategoryFilter;
import org.inventorymgmt.util.NameFilter;
import org.inventorymgmt.util.PriceRangeFilter;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        InventoryService<Product> inventory = new InventoryService<>();

        // Adding products
        inventory.addProduct(new Electronics("E101", "Laptop", 75000, "Dell", 24));
        inventory.addProduct(new Clothing("C202", "T-Shirt", 799, "L", "Black"));
        inventory.addProduct(new Grocery("G303", "Milk", 50, "2025-01-01"));
        inventory.addProduct(new Electronics("E102", "Smartphone", 45000, "Samsung", 12));

        // Retrieving a product
        System.out.println("\nGetting Product:");
        inventory.getProductById("E101").ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Product not found")
        );

        // Filtering by Price Range (500 to 1000)
        System.out.println("\nProducts between Rs. 500 and Rs. 1000:");
        List<Product> priceFiltered = inventory.filterProducts(PriceRangeFilter.byPriceRange(500, 1000));
        priceFiltered.forEach(System.out::println);

        // Filtering by Category (Electronics)
        System.out.println("\nFiltering Electronics:");
        CategoryFilter CategoryFilter = new CategoryFilter();
        List<Product> electronics = inventory.filterProducts(CategoryFilter.byCategory(Electronics.class));
        electronics.forEach(System.out::println);

        // Filtering by Name Keyword ("shirt")
        System.out.println("\nFiltering Products by Name (Keyword: 'shirt'):");
        List<Product> nameFiltered = inventory.filterProducts(NameFilter.byNameContains("shirt"));
        nameFiltered.forEach(System.out::println);

        // Applying a 10% discount to Electronics
        System.out.println("\nApplying 10% discount to Electronics:");
        inventory.applyDiscount(product -> {
            if (product instanceof Electronics) {
                System.out.println(product.getName() + " discounted to: " + (product.getPrice() * 0.9));
            }
        });
    }
}
