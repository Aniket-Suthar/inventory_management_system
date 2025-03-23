package org.inventorymgmt.model;

public class Electronics extends Product {
    private final String brand;
    private final int warranty; // in months

    public Electronics(String id, String name, double price, String brand, int warranty) {
        super(id, name, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() { return brand; }
    public int getWarranty() { return warranty; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Brand: %s, Warranty: %d months", brand, warranty);
    }
}