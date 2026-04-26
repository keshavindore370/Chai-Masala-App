package com.chaisharma.chaimasala.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private double price;

    private String imageUrl;

    private String category;       // e.g. "masala", "tea", "spice"

    private int stockQty = 0;      // inventory count

    private boolean isActive = true; // false = hidden from customers (soft delete)
}
