package org.inventorymgmt.model;


public class Clothing extends Product {
    private final String size;
    private final String color;

    public Clothing(String id, String name, double price, String size, String color) {
        super(id, name, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() { return size; }
    public String getColor() { return color; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Size: %s, Color: %s", size, color);
    }
}
