package com.MarketSystem.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private List<CartItem> items = new ArrayList<>();
    private double total;
    private String status; // PENDING or COMPLETED

    public void calculateTotal() {
        this.total = items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}
