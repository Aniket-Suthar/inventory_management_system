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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryService<Product> inventory = new InventoryService<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== Inventory Management System ==========");
            System.out.println("1. Add a new product");
            System.out.println("2. Retrieve a product by ID");
            System.out.println("3. Filter products by price range");
            System.out.println("4. Filter products by category");
            System.out.println("5. Filter products by name");
            System.out.println("6. Apply discount on category");
            System.out.println("7. View all products");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner, inventory);
                    break;
                case 2:
                    retrieveProduct(scanner, inventory);
                    break;
                case 3:
                    filterByPrice(scanner, inventory);
                    break;
                case 4:
                    filterByCategory(scanner, inventory);
                    break;
                case 5:
                    filterByName(scanner, inventory);
                    break;
                case 6:
                    applyDiscount(scanner, inventory);
                    break;
                case 7:
                    viewAllProducts(inventory);
                    break;
                case 8:
                    System.out.println("Exiting... Thank you for using the Inventory Management System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 8.");
            }
        }
    }

    private static void addProduct(Scanner scanner, InventoryService<Product> inventory) {
        System.out.println("\nChoose product category:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Enter category (1-3): ");

        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        switch (categoryChoice) {
            case 1:
                System.out.print("Enter brand: ");
                String brand = scanner.nextLine();
                System.out.print("Enter warranty (months): ");
                int warranty = scanner.nextInt();
                inventory.addProduct(new Electronics(id, name, price, brand, warranty));
                break;
            case 2:
                System.out.print("Enter size: ");
                String size = scanner.nextLine();
                System.out.print("Enter color: ");
                String color = scanner.nextLine();
                inventory.addProduct(new Clothing(id, name, price, size, color));
                break;
            case 3:
                System.out.print("Enter expiry date (YYYY-MM-DD): ");
                String expiryDate = scanner.nextLine();
                inventory.addProduct(new Grocery(id, name, price, expiryDate));
                break;
            default:
                System.out.println("Invalid category choice!");
                return;
        }
        System.out.println("Product added successfully!");
    }

    private static void retrieveProduct(Scanner scanner, InventoryService<Product> inventory) {
        System.out.print("\nEnter product ID to retrieve: ");
        String id = scanner.nextLine();
        inventory.getProductById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Product not found!")
        );
    }

    private static void filterByPrice(Scanner scanner, InventoryService<Product> inventory) {
        System.out.print("\nEnter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        List<Product> filtered = inventory.filterProducts(PriceRangeFilter.byPriceRange(minPrice, maxPrice));
        if (filtered.isEmpty()) {
            System.out.println("No products found in the given price range.");
        } else {
            filtered.forEach(System.out::println);
        }
    }

    private static void filterByCategory(Scanner scanner, InventoryService<Product> inventory) {
        System.out.println("\nSelect category to filter:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Enter choice (1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Class<? extends Product> category = switch (choice) {
            case 1 -> Electronics.class;
            case 2 -> Clothing.class;
            case 3 -> Grocery.class;
            default -> null;
        };

        if (category != null) {
            List<Product> filtered = inventory.filterProducts(CategoryFilter.byCategory(category));
            if (filtered.isEmpty()) {
                System.out.println("No products found in this category.");
            } else {
                filtered.forEach(System.out::println);
            }
        } else {
            System.out.println("Invalid category choice!");
        }
    }

    private static void filterByName(Scanner scanner, InventoryService<Product> inventory) {
        System.out.print("\nEnter keyword to search in product names: ");
        String keyword = scanner.nextLine();
        List<Product> filtered = inventory.filterProducts(NameFilter.byNameContains(keyword));

        if (filtered.isEmpty()) {
            System.out.println("No products found with the given keyword.");
        } else {
            filtered.forEach(System.out::println);
        }
    }

    private static void applyDiscount(Scanner scanner, InventoryService<Product> inventory) {
        System.out.println("\nSelect category for discount:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Enter choice (1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Class<? extends Product> category = switch (choice) {
            case 1 -> Electronics.class;
            case 2 -> Clothing.class;
            case 3 -> Grocery.class;
            default -> null;
        };

        if (category != null) {
            System.out.print("Enter discount percentage: ");
            double discount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            inventory.applyDiscount(product -> {
                if (category.isInstance(product)) {
                    double discountedPrice = product.getPrice() * (1 - discount / 100);
                    System.out.println(product.getName() + " discounted price: Rs. " + discountedPrice);
                }
            });
        } else {
            System.out.println("Invalid category choice!");
        }
    }

    private static void viewAllProducts(InventoryService<Product> inventory) {
        System.out.println("\nAll Products in Inventory:");
        List<Product> allProducts = inventory.filterProducts(product -> true);
        if (allProducts.isEmpty()) {
            System.out.println("No products available.");
        } else {
            allProducts.forEach(System.out::println);
        }
    }
}
