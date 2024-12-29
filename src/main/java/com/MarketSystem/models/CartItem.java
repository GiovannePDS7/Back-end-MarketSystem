package com.MarketSystem.models;
import lombok.Data;

@Data
public class CartItem {
    private Product product;
    private int quantity;
    private double subtotal;

    public void calculateSubtotal() {
        this.subtotal = this.product.getPrice() * this.quantity;
    }
}
