package org.inventorymgmt.model;

public class Grocery extends Product {
    private final String expiryDate;

    public Grocery(String id, String name, double price, String expiryDate) {
        super(id, name, price);
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() { return expiryDate; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Expiry Date: %s", expiryDate);
    }
}
